package com.Reservations.Konverteri;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.Reservations.DTO.RegistracijaVlasnikaInstruktoraDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RegistracijaKonverter extends AbstractHttpMessageConverter<RegistracijaVlasnikaInstruktoraDTO> {

    private static final FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected boolean supports(Class<?> clazz) {
        return (RegistracijaVlasnikaInstruktoraDTO.class == clazz);
    }

    @Override
    protected RegistracijaVlasnikaInstruktoraDTO readInternal(Class<? extends RegistracijaVlasnikaInstruktoraDTO> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        Map<String, String> vals = formHttpMessageConverter.read(null, inputMessage).toSingleValueMap();
        return mapper.convertValue(vals, RegistracijaVlasnikaInstruktoraDTO.class);
    }

    @Override
    protected void writeInternal(RegistracijaVlasnikaInstruktoraDTO regReq, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

    }
}
