package ru.neoflex.deal.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ru.neoflex.deal.client.CalculatorApiClientImpl;
import ru.neoflex.deal.dao.DAO;
import ru.neoflex.deal.model.Client;
import ru.neoflex.deal.model.Credit;
import ru.neoflex.deal.model.Statement;
import ru.neoflex.deal.model.dto.*;
import ru.neoflex.deal.model.enums.ApplicationStatus;
import ru.neoflex.deal.model.enums.Gender;
import ru.neoflex.deal.utils.TestData;

import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/init_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class DealServiceTest {
    @MockBean
    private CalculatorApiClientImpl calculatorApiClientImpl;
    @Autowired
    private DealService dealService;
    @Autowired
    private DAO<Statement, UUID> statementDAO;
    @Autowired
    private DAO<Client, UUID> clientDAO;

    @Test
    public void calcLoanTermsTest() {
        LoanStatementRequestDto loanStatementRequestDto = TestData.getValidLoanStatementRequestDto();
        Mockito.when(calculatorApiClientImpl.offers(loanStatementRequestDto)).thenReturn(TestData.getTestLoanOffers());
        List<LoanOfferDto> actualOffers = dealService.calcLoanTerms(loanStatementRequestDto);
        actualOffers.forEach(loanOfferDto -> {
            assertNotNull(loanOfferDto.getStatementId());
        });
    }

    @Test
    public void selectLoanOfferTest() {
        UUID statementId = UUID.fromString("b8768740-1b18-4638-a772-5662031aca5a");
        LoanOfferDto loanOfferDto = TestData.getTestLoanOffers().get(0);
        loanOfferDto.setStatementId(statementId);
        dealService.selectLoanOffer(loanOfferDto);
        Statement modStatement = statementDAO.findById(statementId);

        assertEquals(ApplicationStatus.PREPARE_DOCUMENTS, modStatement.getStatus());
        assertEquals(loanOfferDto, modStatement.getAppliedOffer());
        assertEquals(2, modStatement.getStatusHistory().size());
    }

    @Transactional
    @Test
    public void calculateTest() {
        Mockito.when(calculatorApiClientImpl.calc(ArgumentMatchers.any(ScoringDataDto.class))).thenReturn(TestData.getCreditDto());

        String statementId = "d7adafce-04fc-4b12-a18d-db27c86152f8";
        FinishRegistrationRequestDto finishRegistrationRequestDto = TestData.getFinishRegistrationRequestDto();

        dealService.calculate(statementId, finishRegistrationRequestDto);

        Client client = clientDAO.findById(UUID.fromString("f025e3b4-dd2f-4b03-83a0-2aa6eba21160"));
        Statement statement = statementDAO.findById(UUID.fromString(statementId));
        Credit credit = statement.getCredit();

        System.out.println(credit);

        assertEquals(Gender.FEMALE, client.getGender());
        assertNotNull(client.getEmploymentDto());
        assertEquals("2340000.00", credit.getPsk().setScale(2, RoundingMode.HALF_UP).toString());
        assertEquals("48750.00", credit.getMonthlyPayment().setScale(2, RoundingMode.HALF_UP).toString());
    }
}
