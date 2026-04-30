package com.agentworks.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResultTest {

    @Test
    void should_build_success_result_with_payload() {
        Result<String> result = Result.success("ok");

        assertThat(result.getCode()).isEqualTo(200);
        assertThat(result.getMsg()).isEqualTo("success");
        assertThat(result.getData()).isEqualTo("ok");
    }

    @Test
    void should_build_failure_result_with_custom_code() {
        Result<Void> result = Result.failure(400, "bad request");

        assertThat(result.getCode()).isEqualTo(400);
        assertThat(result.getMsg()).isEqualTo("bad request");
        assertThat(result.getData()).isNull();
    }
}
