INSERT INTO client (client_id, last_name, first_name, middle_name,
                    birth_date, email, gender, marital_status,
                    dependent_amount, passport_id, employment_id, account_number)
    VALUES ('e3d917fb-f851-4cad-ba07-6562c65a59cb', 'Nikonov', 'Andrey', 'Sergeevich', '2001-09-13', 'xxxkeep3rxxx@gmail.com',
            'MALE','SINGLE',50000,'{
        "number": "123456",
        "series": "2204",
        "issueDate": [
          2042,
          10,
          13
        ],
        "issueBranch": "Нижегородское что-то там",
        "passportUUID": "eb7ae8bf-aaf1-4e8b-ac69-88fd07efbe6e"
      }', '{
        "salary": 999999,
        "position": "TOP_MANAGER",
        "employerINN": "214264958195",
        "employmentUUID": "ae26b028-b69b-453e-844d-dbe192e65d2b",
        "employmentStatus": "EMPLOYED",
        "workExperienceTotal": 6000,
        "workExperienceCurrent": 3000
      }', '2443532');

INSERT INTO client(client_id, last_name, first_name, middle_name, birth_date, email, passport_id)
VALUES ('34008277-dac8-43d7-a70d-3ffe60288b72', 'Chistov', 'Maxim', 'Sergeevich', '2000-01-08', 'chistov2000@gmail.com',
        '{
          "number": "123456",
          "series": "2204",
          "issueDate": null,
          "issueBranch": null,
          "passportUUID": "d80e0c5e-555f-4e21-80f6-ee7cc46d36a2"
        }');

INSERT INTO client(client_id, last_name, first_name, middle_name, birth_date, email, passport_id)
VALUES ('f025e3b4-dd2f-4b03-83a0-2aa6eba21160', 'Mudrova', 'Olga', 'Viktorovna', '1988-06-19', 'mudrova.ov@gmail.com',
        '{
        "number": "123456",
        "series": "2204",
        "issueDate": null,
        "issueBranch": null,
        "passportUUID": "8d5aa7bb-952f-4708-a031-f7fe5e47dc5a"
      }');

INSERT INTO credit (credit_id, amount, term, monthly_payment, rate,
                    psk, payment_schedule, insurance_enable, salary_client, credit_status)
VALUES ('d917bf37-8786-4e14-9eac-0ef216425f2d', 5000000.00, 10, 49354.17, 15.00, 5922500.00, '[
  {
    "date": [
      2025,
      8,
      15
    ],
    "number": 1,
    "debtPayment": 49354.1666666667,
    "totalPayment": 5922500,
    "remainingDebt": 5873145.8333333333,
    "interestPayment": 0.83
  },
  {
    "date": [
      2025,
      9,
      15
    ],
    "number": 2,
    "debtPayment": 98708.3333333334,
    "totalPayment": 5922500,
    "remainingDebt": 5823791.6666666666,
    "interestPayment": 1.67
  },
  {
    "date": [
      2025,
      10,
      15
    ],
    "number": 3,
    "debtPayment": 148062.5000000001,
    "totalPayment": 5922500,
    "remainingDebt": 5774437.4999999999,
    "interestPayment": 2.5
  },
  {
    "date": [
      2025,
      11,
      15
    ],
    "number": 4,
    "debtPayment": 197416.6666666668,
    "totalPayment": 5922500,
    "remainingDebt": 5725083.3333333332,
    "interestPayment": 3.33
  },
  {
    "date": [
      2025,
      12,
      15
    ],
    "number": 5,
    "debtPayment": 246770.8333333335,
    "totalPayment": 5922500,
    "remainingDebt": 5675729.1666666665,
    "interestPayment": 4.17
  },
  {
    "date": [
      2026,
      1,
      15
    ],
    "number": 6,
    "debtPayment": 296125.0000000002,
    "totalPayment": 5922500,
    "remainingDebt": 5626374.9999999998,
    "interestPayment": 5
  },
  {
    "date": [
      2026,
      2,
      15
    ],
    "number": 7,
    "debtPayment": 345479.1666666669,
    "totalPayment": 5922500,
    "remainingDebt": 5577020.8333333331,
    "interestPayment": 5.83
  },
  {
    "date": [
      2026,
      3,
      15
    ],
    "number": 8,
    "debtPayment": 394833.3333333336,
    "totalPayment": 5922500,
    "remainingDebt": 5527666.6666666664,
    "interestPayment": 6.67
  },
  {
    "date": [
      2026,
      4,
      15
    ],
    "number": 9,
    "debtPayment": 444187.5000000003,
    "totalPayment": 5922500,
    "remainingDebt": 5478312.4999999997,
    "interestPayment": 7.5
  },
  {
    "date": [
      2026,
      5,
      15
    ],
    "number": 10,
    "debtPayment": 493541.666666667,
    "totalPayment": 5922500,
    "remainingDebt": 5428958.333333333,
    "interestPayment": 8.33
  },
  {
    "date": [
      2026,
      6,
      15
    ],
    "number": 11,
    "debtPayment": 542895.8333333337,
    "totalPayment": 5922500,
    "remainingDebt": 5379604.1666666663,
    "interestPayment": 9.17
  },
  {
    "date": [
      2026,
      7,
      15
    ],
    "number": 12,
    "debtPayment": 592250.0000000004,
    "totalPayment": 5922500,
    "remainingDebt": 5330249.9999999996,
    "interestPayment": 10
  },
  {
    "date": [
      2026,
      8,
      15
    ],
    "number": 13,
    "debtPayment": 641604.1666666671,
    "totalPayment": 5922500,
    "remainingDebt": 5280895.8333333329,
    "interestPayment": 10.83
  },
  {
    "date": [
      2026,
      9,
      15
    ],
    "number": 14,
    "debtPayment": 690958.3333333338,
    "totalPayment": 5922500,
    "remainingDebt": 5231541.6666666662,
    "interestPayment": 11.67
  },
  {
    "date": [
      2026,
      10,
      15
    ],
    "number": 15,
    "debtPayment": 740312.5000000005,
    "totalPayment": 5922500,
    "remainingDebt": 5182187.4999999995,
    "interestPayment": 12.5
  },
  {
    "date": [
      2026,
      11,
      15
    ],
    "number": 16,
    "debtPayment": 789666.6666666672,
    "totalPayment": 5922500,
    "remainingDebt": 5132833.3333333328,
    "interestPayment": 13.33
  },
  {
    "date": [
      2026,
      12,
      15
    ],
    "number": 17,
    "debtPayment": 839020.8333333339,
    "totalPayment": 5922500,
    "remainingDebt": 5083479.1666666661,
    "interestPayment": 14.17
  },
  {
    "date": [
      2027,
      1,
      15
    ],
    "number": 18,
    "debtPayment": 888375.0000000006,
    "totalPayment": 5922500,
    "remainingDebt": 5034124.9999999994,
    "interestPayment": 15
  },
  {
    "date": [
      2027,
      2,
      15
    ],
    "number": 19,
    "debtPayment": 937729.1666666673,
    "totalPayment": 5922500,
    "remainingDebt": 4984770.8333333327,
    "interestPayment": 15.83
  },
  {
    "date": [
      2027,
      3,
      15
    ],
    "number": 20,
    "debtPayment": 987083.333333334,
    "totalPayment": 5922500,
    "remainingDebt": 4935416.666666666,
    "interestPayment": 16.67
  },
  {
    "date": [
      2027,
      4,
      15
    ],
    "number": 21,
    "debtPayment": 1036437.5000000007,
    "totalPayment": 5922500,
    "remainingDebt": 4886062.4999999993,
    "interestPayment": 17.5
  },
  {
    "date": [
      2027,
      5,
      15
    ],
    "number": 22,
    "debtPayment": 1085791.6666666674,
    "totalPayment": 5922500,
    "remainingDebt": 4836708.3333333326,
    "interestPayment": 18.33
  },
  {
    "date": [
      2027,
      6,
      15
    ],
    "number": 23,
    "debtPayment": 1135145.8333333341,
    "totalPayment": 5922500,
    "remainingDebt": 4787354.1666666659,
    "interestPayment": 19.17
  },
  {
    "date": [
      2027,
      7,
      15
    ],
    "number": 24,
    "debtPayment": 1184500.0000000008,
    "totalPayment": 5922500,
    "remainingDebt": 4737999.9999999992,
    "interestPayment": 20
  },
  {
    "date": [
      2027,
      8,
      15
    ],
    "number": 25,
    "debtPayment": 1233854.1666666675,
    "totalPayment": 5922500,
    "remainingDebt": 4688645.8333333325,
    "interestPayment": 20.83
  },
  {
    "date": [
      2027,
      9,
      15
    ],
    "number": 26,
    "debtPayment": 1283208.3333333342,
    "totalPayment": 5922500,
    "remainingDebt": 4639291.6666666658,
    "interestPayment": 21.67
  },
  {
    "date": [
      2027,
      10,
      15
    ],
    "number": 27,
    "debtPayment": 1332562.5000000009,
    "totalPayment": 5922500,
    "remainingDebt": 4589937.4999999991,
    "interestPayment": 22.5
  },
  {
    "date": [
      2027,
      11,
      15
    ],
    "number": 28,
    "debtPayment": 1381916.6666666676,
    "totalPayment": 5922500,
    "remainingDebt": 4540583.3333333324,
    "interestPayment": 23.33
  },
  {
    "date": [
      2027,
      12,
      15
    ],
    "number": 29,
    "debtPayment": 1431270.8333333343,
    "totalPayment": 5922500,
    "remainingDebt": 4491229.1666666657,
    "interestPayment": 24.17
  },
  {
    "date": [
      2028,
      1,
      15
    ],
    "number": 30,
    "debtPayment": 1480625.000000001,
    "totalPayment": 5922500,
    "remainingDebt": 4441874.999999999,
    "interestPayment": 25
  },
  {
    "date": [
      2028,
      2,
      15
    ],
    "number": 31,
    "debtPayment": 1529979.1666666677,
    "totalPayment": 5922500,
    "remainingDebt": 4392520.8333333323,
    "interestPayment": 25.83
  },
  {
    "date": [
      2028,
      3,
      15
    ],
    "number": 32,
    "debtPayment": 1579333.3333333344,
    "totalPayment": 5922500,
    "remainingDebt": 4343166.6666666656,
    "interestPayment": 26.67
  },
  {
    "date": [
      2028,
      4,
      15
    ],
    "number": 33,
    "debtPayment": 1628687.5000000011,
    "totalPayment": 5922500,
    "remainingDebt": 4293812.4999999989,
    "interestPayment": 27.5
  },
  {
    "date": [
      2028,
      5,
      15
    ],
    "number": 34,
    "debtPayment": 1678041.6666666678,
    "totalPayment": 5922500,
    "remainingDebt": 4244458.3333333322,
    "interestPayment": 28.33
  },
  {
    "date": [
      2028,
      6,
      15
    ],
    "number": 35,
    "debtPayment": 1727395.8333333345,
    "totalPayment": 5922500,
    "remainingDebt": 4195104.1666666655,
    "interestPayment": 29.17
  },
  {
    "date": [
      2028,
      7,
      15
    ],
    "number": 36,
    "debtPayment": 1776750.0000000012,
    "totalPayment": 5922500,
    "remainingDebt": 4145749.9999999988,
    "interestPayment": 30
  },
  {
    "date": [
      2028,
      8,
      15
    ],
    "number": 37,
    "debtPayment": 1826104.1666666679,
    "totalPayment": 5922500,
    "remainingDebt": 4096395.8333333321,
    "interestPayment": 30.83
  },
  {
    "date": [
      2028,
      9,
      15
    ],
    "number": 38,
    "debtPayment": 1875458.3333333346,
    "totalPayment": 5922500,
    "remainingDebt": 4047041.6666666654,
    "interestPayment": 31.67
  },
  {
    "date": [
      2028,
      10,
      15
    ],
    "number": 39,
    "debtPayment": 1924812.5000000013,
    "totalPayment": 5922500,
    "remainingDebt": 3997687.4999999987,
    "interestPayment": 32.5
  },
  {
    "date": [
      2028,
      11,
      15
    ],
    "number": 40,
    "debtPayment": 1974166.666666668,
    "totalPayment": 5922500,
    "remainingDebt": 3948333.333333332,
    "interestPayment": 33.33
  },
  {
    "date": [
      2028,
      12,
      15
    ],
    "number": 41,
    "debtPayment": 2023520.8333333347,
    "totalPayment": 5922500,
    "remainingDebt": 3898979.1666666653,
    "interestPayment": 34.17
  },
  {
    "date": [
      2029,
      1,
      15
    ],
    "number": 42,
    "debtPayment": 2072875.0000000014,
    "totalPayment": 5922500,
    "remainingDebt": 3849624.9999999986,
    "interestPayment": 35
  },
  {
    "date": [
      2029,
      2,
      15
    ],
    "number": 43,
    "debtPayment": 2122229.1666666681,
    "totalPayment": 5922500,
    "remainingDebt": 3800270.8333333319,
    "interestPayment": 35.83
  },
  {
    "date": [
      2029,
      3,
      15
    ],
    "number": 44,
    "debtPayment": 2171583.3333333348,
    "totalPayment": 5922500,
    "remainingDebt": 3750916.6666666652,
    "interestPayment": 36.67
  },
  {
    "date": [
      2029,
      4,
      15
    ],
    "number": 45,
    "debtPayment": 2220937.5000000015,
    "totalPayment": 5922500,
    "remainingDebt": 3701562.4999999985,
    "interestPayment": 37.5
  },
  {
    "date": [
      2029,
      5,
      15
    ],
    "number": 46,
    "debtPayment": 2270291.6666666682,
    "totalPayment": 5922500,
    "remainingDebt": 3652208.3333333318,
    "interestPayment": 38.33
  },
  {
    "date": [
      2029,
      6,
      15
    ],
    "number": 47,
    "debtPayment": 2319645.8333333349,
    "totalPayment": 5922500,
    "remainingDebt": 3602854.1666666651,
    "interestPayment": 39.17
  },
  {
    "date": [
      2029,
      7,
      15
    ],
    "number": 48,
    "debtPayment": 2369000.0000000016,
    "totalPayment": 5922500,
    "remainingDebt": 3553499.9999999984,
    "interestPayment": 40
  },
  {
    "date": [
      2029,
      8,
      15
    ],
    "number": 49,
    "debtPayment": 2418354.1666666683,
    "totalPayment": 5922500,
    "remainingDebt": 3504145.8333333317,
    "interestPayment": 40.83
  },
  {
    "date": [
      2029,
      9,
      15
    ],
    "number": 50,
    "debtPayment": 2467708.333333335,
    "totalPayment": 5922500,
    "remainingDebt": 3454791.666666665,
    "interestPayment": 41.67
  },
  {
    "date": [
      2029,
      10,
      15
    ],
    "number": 51,
    "debtPayment": 2517062.5000000017,
    "totalPayment": 5922500,
    "remainingDebt": 3405437.4999999983,
    "interestPayment": 42.5
  },
  {
    "date": [
      2029,
      11,
      15
    ],
    "number": 52,
    "debtPayment": 2566416.6666666684,
    "totalPayment": 5922500,
    "remainingDebt": 3356083.3333333316,
    "interestPayment": 43.33
  },
  {
    "date": [
      2029,
      12,
      15
    ],
    "number": 53,
    "debtPayment": 2615770.8333333351,
    "totalPayment": 5922500,
    "remainingDebt": 3306729.1666666649,
    "interestPayment": 44.17
  },
  {
    "date": [
      2030,
      1,
      15
    ],
    "number": 54,
    "debtPayment": 2665125.0000000018,
    "totalPayment": 5922500,
    "remainingDebt": 3257374.9999999982,
    "interestPayment": 45
  },
  {
    "date": [
      2030,
      2,
      15
    ],
    "number": 55,
    "debtPayment": 2714479.1666666685,
    "totalPayment": 5922500,
    "remainingDebt": 3208020.8333333315,
    "interestPayment": 45.83
  },
  {
    "date": [
      2030,
      3,
      15
    ],
    "number": 56,
    "debtPayment": 2763833.3333333352,
    "totalPayment": 5922500,
    "remainingDebt": 3158666.6666666648,
    "interestPayment": 46.67
  },
  {
    "date": [
      2030,
      4,
      15
    ],
    "number": 57,
    "debtPayment": 2813187.5000000019,
    "totalPayment": 5922500,
    "remainingDebt": 3109312.4999999981,
    "interestPayment": 47.5
  },
  {
    "date": [
      2030,
      5,
      15
    ],
    "number": 58,
    "debtPayment": 2862541.6666666686,
    "totalPayment": 5922500,
    "remainingDebt": 3059958.3333333314,
    "interestPayment": 48.33
  },
  {
    "date": [
      2030,
      6,
      15
    ],
    "number": 59,
    "debtPayment": 2911895.8333333353,
    "totalPayment": 5922500,
    "remainingDebt": 3010604.1666666647,
    "interestPayment": 49.17
  },
  {
    "date": [
      2030,
      7,
      15
    ],
    "number": 60,
    "debtPayment": 2961250.000000002,
    "totalPayment": 5922500,
    "remainingDebt": 2961249.999999998,
    "interestPayment": 50
  },
  {
    "date": [
      2030,
      8,
      15
    ],
    "number": 61,
    "debtPayment": 3010604.1666666687,
    "totalPayment": 5922500,
    "remainingDebt": 2911895.8333333313,
    "interestPayment": 50.83
  },
  {
    "date": [
      2030,
      9,
      15
    ],
    "number": 62,
    "debtPayment": 3059958.3333333354,
    "totalPayment": 5922500,
    "remainingDebt": 2862541.6666666646,
    "interestPayment": 51.67
  },
  {
    "date": [
      2030,
      10,
      15
    ],
    "number": 63,
    "debtPayment": 3109312.5000000021,
    "totalPayment": 5922500,
    "remainingDebt": 2813187.4999999979,
    "interestPayment": 52.5
  },
  {
    "date": [
      2030,
      11,
      15
    ],
    "number": 64,
    "debtPayment": 3158666.6666666688,
    "totalPayment": 5922500,
    "remainingDebt": 2763833.3333333312,
    "interestPayment": 53.33
  },
  {
    "date": [
      2030,
      12,
      15
    ],
    "number": 65,
    "debtPayment": 3208020.8333333355,
    "totalPayment": 5922500,
    "remainingDebt": 2714479.1666666645,
    "interestPayment": 54.17
  },
  {
    "date": [
      2031,
      1,
      15
    ],
    "number": 66,
    "debtPayment": 3257375.0000000022,
    "totalPayment": 5922500,
    "remainingDebt": 2665124.9999999978,
    "interestPayment": 55
  },
  {
    "date": [
      2031,
      2,
      15
    ],
    "number": 67,
    "debtPayment": 3306729.1666666689,
    "totalPayment": 5922500,
    "remainingDebt": 2615770.8333333311,
    "interestPayment": 55.83
  },
  {
    "date": [
      2031,
      3,
      15
    ],
    "number": 68,
    "debtPayment": 3356083.3333333356,
    "totalPayment": 5922500,
    "remainingDebt": 2566416.6666666644,
    "interestPayment": 56.67
  },
  {
    "date": [
      2031,
      4,
      15
    ],
    "number": 69,
    "debtPayment": 3405437.5000000023,
    "totalPayment": 5922500,
    "remainingDebt": 2517062.4999999977,
    "interestPayment": 57.5
  },
  {
    "date": [
      2031,
      5,
      15
    ],
    "number": 70,
    "debtPayment": 3454791.666666669,
    "totalPayment": 5922500,
    "remainingDebt": 2467708.333333331,
    "interestPayment": 58.33
  },
  {
    "date": [
      2031,
      6,
      15
    ],
    "number": 71,
    "debtPayment": 3504145.8333333357,
    "totalPayment": 5922500,
    "remainingDebt": 2418354.1666666643,
    "interestPayment": 59.17
  },
  {
    "date": [
      2031,
      7,
      15
    ],
    "number": 72,
    "debtPayment": 3553500.0000000024,
    "totalPayment": 5922500,
    "remainingDebt": 2368999.9999999976,
    "interestPayment": 60
  },
  {
    "date": [
      2031,
      8,
      15
    ],
    "number": 73,
    "debtPayment": 3602854.1666666691,
    "totalPayment": 5922500,
    "remainingDebt": 2319645.8333333309,
    "interestPayment": 60.83
  },
  {
    "date": [
      2031,
      9,
      15
    ],
    "number": 74,
    "debtPayment": 3652208.3333333358,
    "totalPayment": 5922500,
    "remainingDebt": 2270291.6666666642,
    "interestPayment": 61.67
  },
  {
    "date": [
      2031,
      10,
      15
    ],
    "number": 75,
    "debtPayment": 3701562.5000000025,
    "totalPayment": 5922500,
    "remainingDebt": 2220937.4999999975,
    "interestPayment": 62.5
  },
  {
    "date": [
      2031,
      11,
      15
    ],
    "number": 76,
    "debtPayment": 3750916.6666666692,
    "totalPayment": 5922500,
    "remainingDebt": 2171583.3333333308,
    "interestPayment": 63.33
  },
  {
    "date": [
      2031,
      12,
      15
    ],
    "number": 77,
    "debtPayment": 3800270.8333333359,
    "totalPayment": 5922500,
    "remainingDebt": 2122229.1666666641,
    "interestPayment": 64.17
  },
  {
    "date": [
      2032,
      1,
      15
    ],
    "number": 78,
    "debtPayment": 3849625.0000000026,
    "totalPayment": 5922500,
    "remainingDebt": 2072874.9999999974,
    "interestPayment": 65
  },
  {
    "date": [
      2032,
      2,
      15
    ],
    "number": 79,
    "debtPayment": 3898979.1666666693,
    "totalPayment": 5922500,
    "remainingDebt": 2023520.8333333307,
    "interestPayment": 65.83
  },
  {
    "date": [
      2032,
      3,
      15
    ],
    "number": 80,
    "debtPayment": 3948333.333333336,
    "totalPayment": 5922500,
    "remainingDebt": 1974166.666666664,
    "interestPayment": 66.67
  },
  {
    "date": [
      2032,
      4,
      15
    ],
    "number": 81,
    "debtPayment": 3997687.5000000027,
    "totalPayment": 5922500,
    "remainingDebt": 1924812.4999999973,
    "interestPayment": 67.5
  },
  {
    "date": [
      2032,
      5,
      15
    ],
    "number": 82,
    "debtPayment": 4047041.6666666694,
    "totalPayment": 5922500,
    "remainingDebt": 1875458.3333333306,
    "interestPayment": 68.33
  },
  {
    "date": [
      2032,
      6,
      15
    ],
    "number": 83,
    "debtPayment": 4096395.8333333361,
    "totalPayment": 5922500,
    "remainingDebt": 1826104.1666666639,
    "interestPayment": 69.17
  },
  {
    "date": [
      2032,
      7,
      15
    ],
    "number": 84,
    "debtPayment": 4145750.0000000028,
    "totalPayment": 5922500,
    "remainingDebt": 1776749.9999999972,
    "interestPayment": 70
  },
  {
    "date": [
      2032,
      8,
      15
    ],
    "number": 85,
    "debtPayment": 4195104.1666666695,
    "totalPayment": 5922500,
    "remainingDebt": 1727395.8333333305,
    "interestPayment": 70.83
  },
  {
    "date": [
      2032,
      9,
      15
    ],
    "number": 86,
    "debtPayment": 4244458.3333333362,
    "totalPayment": 5922500,
    "remainingDebt": 1678041.6666666638,
    "interestPayment": 71.67
  },
  {
    "date": [
      2032,
      10,
      15
    ],
    "number": 87,
    "debtPayment": 4293812.5000000029,
    "totalPayment": 5922500,
    "remainingDebt": 1628687.4999999971,
    "interestPayment": 72.5
  },
  {
    "date": [
      2032,
      11,
      15
    ],
    "number": 88,
    "debtPayment": 4343166.6666666696,
    "totalPayment": 5922500,
    "remainingDebt": 1579333.3333333304,
    "interestPayment": 73.33
  },
  {
    "date": [
      2032,
      12,
      15
    ],
    "number": 89,
    "debtPayment": 4392520.8333333363,
    "totalPayment": 5922500,
    "remainingDebt": 1529979.1666666637,
    "interestPayment": 74.17
  },
  {
    "date": [
      2033,
      1,
      15
    ],
    "number": 90,
    "debtPayment": 4441875.000000003,
    "totalPayment": 5922500,
    "remainingDebt": 1480624.999999997,
    "interestPayment": 75
  },
  {
    "date": [
      2033,
      2,
      15
    ],
    "number": 91,
    "debtPayment": 4491229.1666666697,
    "totalPayment": 5922500,
    "remainingDebt": 1431270.8333333303,
    "interestPayment": 75.83
  },
  {
    "date": [
      2033,
      3,
      15
    ],
    "number": 92,
    "debtPayment": 4540583.3333333364,
    "totalPayment": 5922500,
    "remainingDebt": 1381916.6666666636,
    "interestPayment": 76.67
  },
  {
    "date": [
      2033,
      4,
      15
    ],
    "number": 93,
    "debtPayment": 4589937.5000000031,
    "totalPayment": 5922500,
    "remainingDebt": 1332562.4999999969,
    "interestPayment": 77.5
  },
  {
    "date": [
      2033,
      5,
      15
    ],
    "number": 94,
    "debtPayment": 4639291.6666666698,
    "totalPayment": 5922500,
    "remainingDebt": 1283208.3333333302,
    "interestPayment": 78.33
  },
  {
    "date": [
      2033,
      6,
      15
    ],
    "number": 95,
    "debtPayment": 4688645.8333333365,
    "totalPayment": 5922500,
    "remainingDebt": 1233854.1666666635,
    "interestPayment": 79.17
  },
  {
    "date": [
      2033,
      7,
      15
    ],
    "number": 96,
    "debtPayment": 4738000.0000000032,
    "totalPayment": 5922500,
    "remainingDebt": 1184499.9999999968,
    "interestPayment": 80
  },
  {
    "date": [
      2033,
      8,
      15
    ],
    "number": 97,
    "debtPayment": 4787354.1666666699,
    "totalPayment": 5922500,
    "remainingDebt": 1135145.8333333301,
    "interestPayment": 80.83
  },
  {
    "date": [
      2033,
      9,
      15
    ],
    "number": 98,
    "debtPayment": 4836708.3333333366,
    "totalPayment": 5922500,
    "remainingDebt": 1085791.6666666634,
    "interestPayment": 81.67
  },
  {
    "date": [
      2033,
      10,
      15
    ],
    "number": 99,
    "debtPayment": 4886062.5000000033,
    "totalPayment": 5922500,
    "remainingDebt": 1036437.4999999967,
    "interestPayment": 82.5
  },
  {
    "date": [
      2033,
      11,
      15
    ],
    "number": 100,
    "debtPayment": 4935416.66666667,
    "totalPayment": 5922500,
    "remainingDebt": 987083.33333333,
    "interestPayment": 83.33
  },
  {
    "date": [
      2033,
      12,
      15
    ],
    "number": 101,
    "debtPayment": 4984770.8333333367,
    "totalPayment": 5922500,
    "remainingDebt": 937729.1666666633,
    "interestPayment": 84.17
  },
  {
    "date": [
      2034,
      1,
      15
    ],
    "number": 102,
    "debtPayment": 5034125.0000000034,
    "totalPayment": 5922500,
    "remainingDebt": 888374.9999999966,
    "interestPayment": 85
  },
  {
    "date": [
      2034,
      2,
      15
    ],
    "number": 103,
    "debtPayment": 5083479.1666666701,
    "totalPayment": 5922500,
    "remainingDebt": 839020.8333333299,
    "interestPayment": 85.83
  },
  {
    "date": [
      2034,
      3,
      15
    ],
    "number": 104,
    "debtPayment": 5132833.3333333368,
    "totalPayment": 5922500,
    "remainingDebt": 789666.6666666632,
    "interestPayment": 86.67
  },
  {
    "date": [
      2034,
      4,
      15
    ],
    "number": 105,
    "debtPayment": 5182187.5000000035,
    "totalPayment": 5922500,
    "remainingDebt": 740312.4999999965,
    "interestPayment": 87.5
  },
  {
    "date": [
      2034,
      5,
      15
    ],
    "number": 106,
    "debtPayment": 5231541.6666666702,
    "totalPayment": 5922500,
    "remainingDebt": 690958.3333333298,
    "interestPayment": 88.33
  },
  {
    "date": [
      2034,
      6,
      15
    ],
    "number": 107,
    "debtPayment": 5280895.8333333369,
    "totalPayment": 5922500,
    "remainingDebt": 641604.1666666631,
    "interestPayment": 89.17
  },
  {
    "date": [
      2034,
      7,
      15
    ],
    "number": 108,
    "debtPayment": 5330250.0000000036,
    "totalPayment": 5922500,
    "remainingDebt": 592249.9999999964,
    "interestPayment": 90
  },
  {
    "date": [
      2034,
      8,
      15
    ],
    "number": 109,
    "debtPayment": 5379604.1666666703,
    "totalPayment": 5922500,
    "remainingDebt": 542895.8333333297,
    "interestPayment": 90.83
  },
  {
    "date": [
      2034,
      9,
      15
    ],
    "number": 110,
    "debtPayment": 5428958.333333337,
    "totalPayment": 5922500,
    "remainingDebt": 493541.666666663,
    "interestPayment": 91.67
  },
  {
    "date": [
      2034,
      10,
      15
    ],
    "number": 111,
    "debtPayment": 5478312.5000000037,
    "totalPayment": 5922500,
    "remainingDebt": 444187.4999999963,
    "interestPayment": 92.5
  },
  {
    "date": [
      2034,
      11,
      15
    ],
    "number": 112,
    "debtPayment": 5527666.6666666704,
    "totalPayment": 5922500,
    "remainingDebt": 394833.3333333296,
    "interestPayment": 93.33
  },
  {
    "date": [
      2034,
      12,
      15
    ],
    "number": 113,
    "debtPayment": 5577020.8333333371,
    "totalPayment": 5922500,
    "remainingDebt": 345479.1666666629,
    "interestPayment": 94.17
  },
  {
    "date": [
      2035,
      1,
      15
    ],
    "number": 114,
    "debtPayment": 5626375.0000000038,
    "totalPayment": 5922500,
    "remainingDebt": 296124.9999999962,
    "interestPayment": 95
  },
  {
    "date": [
      2035,
      2,
      15
    ],
    "number": 115,
    "debtPayment": 5675729.1666666705,
    "totalPayment": 5922500,
    "remainingDebt": 246770.8333333295,
    "interestPayment": 95.83
  },
  {
    "date": [
      2035,
      3,
      15
    ],
    "number": 116,
    "debtPayment": 5725083.3333333372,
    "totalPayment": 5922500,
    "remainingDebt": 197416.6666666628,
    "interestPayment": 96.67
  },
  {
    "date": [
      2035,
      4,
      15
    ],
    "number": 117,
    "debtPayment": 5774437.5000000039,
    "totalPayment": 5922500,
    "remainingDebt": 148062.4999999961,
    "interestPayment": 97.5
  },
  {
    "date": [
      2035,
      5,
      15
    ],
    "number": 118,
    "debtPayment": 5823791.6666666706,
    "totalPayment": 5922500,
    "remainingDebt": 98708.3333333294,
    "interestPayment": 98.33
  },
  {
    "date": [
      2035,
      6,
      15
    ],
    "number": 119,
    "debtPayment": 5873145.8333333373,
    "totalPayment": 5922500,
    "remainingDebt": 49354.1666666627,
    "interestPayment": 99.17
  },
  {
    "date": [
      2035,
      7,
      15
    ],
    "number": 120,
    "debtPayment": 5922500.000000004,
    "totalPayment": 5922500,
    "remainingDebt": -4e-9,
    "interestPayment": 100
  }
]', true, true, 'CALCULATED');

INSERT INTO statement (statement_id, client_id, credit_id, status, creation_date, applied_offer, sign_date, ses_code, status_history)
VALUES
('47936ce8-e8c6-496e-a33a-f8f1d4c26c74', 'e3d917fb-f851-4cad-ba07-6562c65a59cb', 'd917bf37-8786-4e14-9eac-0ef216425f2d', 'APPROVED', '2025-08-15',
 '{
   "rate": 15,
   "term": 10,
   "statementId": "47936ce8-e8c6-496e-a33a-f8f1d4c26c74",
   "totalAmount": 5922500,
   "salaryClient": true,
   "monthlyPayment": 49354.1666666667,
   "requestedAmount": 5000000,
   "insuranceEnabled": true
 }',
 '2025-08-15 22:35:00', null,
 '[
   {
     "time": [
       2025,
       8,
       15
     ],
     "status": "create statement",
     "changeType": "AUTOMATIC"
   },
   {
     "time": [
       2025,
       8,
       15
     ],
     "status": "a loan offer has been selected",
     "changeType": "AUTOMATIC"
   },
   {
     "time": [
       2025,
       8,
       15
     ],
     "status": "credit approved",
     "changeType": "AUTOMATIC"
   }
 ]');

INSERT INTO statement (statement_id, client_id, status, creation_date, status_history)
VALUES ('b8768740-1b18-4638-a772-5662031aca5a', '34008277-dac8-43d7-a70d-3ffe60288b72', 'DOCUMENT_CREATED', '2025-08-17',
        '[
          {
            "time": [
              2025,
              8,
              17
            ],
            "status": "create statement",
            "changeType": "AUTOMATIC"
          }
        ]');

INSERT INTO statement (statement_id, client_id, status, creation_date, applied_offer, status_history)
VALUES ('d7adafce-04fc-4b12-a18d-db27c86152f8', 'f025e3b4-dd2f-4b03-83a0-2aa6eba21160', 'PREPARE_DOCUMENTS', '2025-08-17',
        '{
          "rate": 17,
          "term": 4,
          "statementId": "d7adafce-04fc-4b12-a18d-db27c86152f8",
          "totalAmount": 2340000,
          "salaryClient": true,
          "monthlyPayment": 48750,
          "requestedAmount": 2000000,
          "insuranceEnabled": false
        }',
        '[
          {
            "time": [
              2025,
              8,
              17
            ],
            "status": "create statement",
            "changeType": "AUTOMATIC"
          },
          {
            "time": [
              2025,
              8,
              17
            ],
            "status": "a loan offer has been selected",
            "changeType": "AUTOMATIC"
          }
        ]');