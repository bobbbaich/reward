package com.bobbbaich.reward.controller;

import com.bobbbaich.reward.dto.CreateGiftDTO;
import com.bobbbaich.reward.dto.GiftDTO;
import com.bobbbaich.reward.dto.UpdateGiftDTO;
import com.bobbbaich.reward.repository.GiftRepository;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

class GiftControllerTest extends MvcIntegrationTest {

    private static final String SCOPE_ROLE_ADMIN = "SCOPE_ROLE_ADMIN";

    @Autowired
    private GiftRepository giftRepository;

    @BeforeEach
    void setUp() {
        configureRestAssured();
        cleanupDatabase();
    }

    @AfterEach
    void tearDown() {
        cleanupDatabase();
    }

    void cleanupDatabase() {
        giftRepository.deleteAll();
    }

    @Test
    void testCreate() {
        CreateGiftDTO createDTO = getCreateGiftDTO();

        GiftDTO giftDTO = createGiftSuccessfully(createDTO);

        assertThat(giftDTO.getUuid(), notNullValue());
        assertEquals(createDTO.getName(), giftDTO.getName());
        assertThat(giftDTO.getAuditMetadata().getCreatedAt(), lessThanOrEqualTo(ZonedDateTime.now()));
        assertThat(giftDTO.getAuditMetadata().getUpdatedAt(), lessThanOrEqualTo(ZonedDateTime.now()));
        assertThat(giftDTO.getAuditMetadata().getCreatedBy(), not(blankOrNullString()));
        assertThat(giftDTO.getAuditMetadata().getUpdatedBy(), is(giftDTO.getAuditMetadata().getCreatedBy()));
    }

    @Test
    void testReadAll() {
        GiftDTO giftDTO1 = createGiftSuccessfully(getCreateGiftDTO());
        GiftDTO giftDTO2 = createGiftSuccessfully(getCreateGiftDTO());

        SecurityContextHolder.clearContext();

        List<GiftDTO> giftDTOs = given()
                .auth().with(jwt().authorities(getAuthorities(SCOPE_ROLE_ADMIN)))
                .when()
                .get("/gifts")
                .then()
                .statusCode(OK.value())
                .extract()
                .jsonPath()
                .getList("content", GiftDTO.class);

        assertEquals(giftDTO2.getUuid(), giftDTOs.get(0).getUuid());
        assertEquals(giftDTO1.getUuid(), giftDTOs.get(1).getUuid());
    }

    @Test
    void testRead() {
        CreateGiftDTO createDTO = getCreateGiftDTO();
        GiftDTO createdGift = createGiftSuccessfully(createDTO);

        GiftDTO readGiftDTO = readGiftSuccessfully(createdGift.getUuid());

        assertEquals(createdGift.getUuid(), readGiftDTO.getUuid());
    }

    @Test
    void testUpdate() {
        CreateGiftDTO createDTO = getCreateGiftDTO();
        GiftDTO createdGift = createGiftSuccessfully(createDTO);

        UpdateGiftDTO updateDTO = getUpdateGiftDTO();
        GiftDTO updateGiftDTO = updateGiftSuccessfully(createdGift.getUuid(), updateDTO);

        assertEquals(createdGift.getUuid(), updateGiftDTO.getUuid());
        assertEquals(updateDTO.getName(), updateGiftDTO.getName());
        assertThat(updateGiftDTO.getAuditMetadata().getCreatedAt(), lessThanOrEqualTo(updateGiftDTO.getAuditMetadata().getUpdatedAt()));
        assertThat(updateGiftDTO.getAuditMetadata().getCreatedBy(), not(blankOrNullString()));
        assertThat(updateGiftDTO.getAuditMetadata().getUpdatedBy(), is(updateGiftDTO.getAuditMetadata().getCreatedBy()));
    }

    @Test
    void testDelete() {
        CreateGiftDTO createDTO = getCreateGiftDTO();

        GiftDTO createdGift = createGiftSuccessfully(createDTO);

        given()
                .auth().with(jwt().authorities(getAuthorities(SCOPE_ROLE_ADMIN)))
                .when()
                .delete("/gifts/{uuid}", createdGift.getUuid())
                .then()
                .statusCode(NO_CONTENT.value());

    }

    private GiftDTO createGiftSuccessfully(CreateGiftDTO createDTO) {
        return createGift(createDTO)
                .then()
                .statusCode(CREATED.value())
                .extract()
                .as(GiftDTO.class);
    }

    private MockMvcResponse createGift(CreateGiftDTO createDTO) {
        return given()
                .body(createDTO)
                .auth().with(jwt().authorities(getAuthorities(SCOPE_ROLE_ADMIN)))
                .contentType(JSON)
                .post("/gifts");
    }

    private GiftDTO updateGiftSuccessfully(UUID uuid, UpdateGiftDTO updateDTO) {
        return updateGift(uuid, updateDTO)
                .then()
                .statusCode(OK.value())
                .extract()
                .as(GiftDTO.class);
    }

    private MockMvcResponse updateGift(UUID uuid, UpdateGiftDTO updateDTO) {
        return given()
                .auth().with(jwt().authorities(getAuthorities(SCOPE_ROLE_ADMIN)))
                .body(updateDTO)
                .contentType(JSON)
                .put("/gifts/{uuid}", uuid);
    }

    private GiftDTO readGiftSuccessfully(UUID uuid) {
        return readGift(uuid)
                .then()
                .statusCode(OK.value())
                .extract()
                .as(GiftDTO.class);
    }

    private MockMvcResponse readGift(UUID uuid) {
        return given()
                .auth().with(jwt().authorities(getAuthorities(SCOPE_ROLE_ADMIN)))
                .when()
                .get("/gifts/{uuid}", uuid);
    }

    private CreateGiftDTO getCreateGiftDTO() {
        UUID uuid = UUID.randomUUID();
        return CreateGiftDTO.builder()
                .name("name_" + uuid)
                .build();
    }

    private UpdateGiftDTO getUpdateGiftDTO() {
        UUID uuid = UUID.randomUUID();
        return UpdateGiftDTO.builder()
                .name("updated_name_" + uuid)
                .build();
    }
}