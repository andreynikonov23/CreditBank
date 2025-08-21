package ru.neoflex.model;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.neoflex.dto.LoanOfferDto;
import ru.neoflex.dto.StatementStatusHistoryDto;
import ru.neoflex.enums.ApplicationStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Statement {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "statement_id")
    private UUID id;
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
    @Timestamp
    @Column(name = "creation_date")
    private LocalDate creationDate;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "applied_offer")
    private LoanOfferDto appliedOffer;
    @Timestamp
    @Column(name = "sign_date")
    private LocalDate signDate;
    @Column(name = "ses_code")
    private String sesCode;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "status_history")
    private List<StatementStatusHistoryDto> statusHistory;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_id")
    private Credit credit;

    @Override
    public String toString() {
        return String.format("Statement{id: %s, status: %s, creationDate: %s, appliedOffer: %s, signDate: %s," +
                        " sesCode: %s, statusHistory: %s, client: %s, credit: %s}",
                id, status, creationDate, appliedOffer, signDate, sesCode, statusHistory, client, credit);
    }
}
