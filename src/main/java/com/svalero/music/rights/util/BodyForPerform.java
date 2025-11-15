package com.svalero.music.rights.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BodyForPerform {

    String bodyMusician = """
                {
                  "firstName": "Aritz",
                  "lastName": "Ontalvilla",
                  "birthDate": "1992-09-12",
                  "affiliated": true,
                  "dni": "45916040J",
                  "performanceFee": 6.0,
                  "affiliatedNumber": 12313,
                  "works": [
                    { "id": 999 }
                  ]
                }
                """;
    }
