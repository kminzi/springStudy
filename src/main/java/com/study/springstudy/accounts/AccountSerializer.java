package com.study.springstudy.accounts;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

//모든 account에 이 serializer를 설정하는 것이 아니라, 필요할 때만 선언해서 사용(여기서는 event 객체에만)
public class AccountSerializer extends JsonSerializer<Account> {
    @Override
    public void serialize(Account account, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id",account.getId());
        gen.writeEndObject();
    }
}
