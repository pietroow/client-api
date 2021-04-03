package io.platformbuilders.client_api.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtil {

    public static long calcularIdade(LocalDate dataNascimento) {
        LocalDate dataAtual = LocalDate.now();
        return ChronoUnit.YEARS.between(dataNascimento, dataAtual);
    }

}
