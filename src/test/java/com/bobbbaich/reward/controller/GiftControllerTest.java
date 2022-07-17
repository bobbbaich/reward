package com.bobbbaich.reward.controller;

import com.bobbbaich.reward.dto.CreateGiftDTO;
import com.bobbbaich.reward.dto.GiftDTO;
import com.bobbbaich.reward.dto.UpdateGiftDTO;
import com.bobbbaich.reward.repository.GiftRepository;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

class GiftControllerTest extends MvcIntegrationTest {

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
        assertThat(giftDTO.getCreatedAt(), lessThanOrEqualTo(ZonedDateTime.now()));
        assertThat(giftDTO.getUpdatedAt(), lessThanOrEqualTo(ZonedDateTime.now()));
    }

    @Test
    void testReadAll() {
        GiftDTO giftDTO1 = createGiftSuccessfully(getCreateGiftDTO());
        GiftDTO giftDTO2 = createGiftSuccessfully(getCreateGiftDTO());

        List<GiftDTO> giftDTOs = when()
                .get("/api/rewards/gifts")
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
        assertThat(updateGiftDTO.getCreatedAt(), lessThanOrEqualTo(updateGiftDTO.getUpdatedAt()));
    }

    @Test
    void testDelete() {
        CreateGiftDTO createDTO = getCreateGiftDTO();

        GiftDTO createdGift = createGiftSuccessfully(createDTO);

        when()
                .delete("/api/rewards/gifts/{uuid}", createdGift.getUuid())
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

    private Response createGift(CreateGiftDTO createDTO) {
        return with()
                .body(createDTO)
                .contentType(JSON)
                .post("/api/rewards/gifts");
    }

    private GiftDTO updateGiftSuccessfully(UUID uuid, UpdateGiftDTO updateDTO) {
        return updateGift(uuid, updateDTO)
                .then()
                .statusCode(OK.value())
                .extract()
                .as(GiftDTO.class);
    }

    private Response updateGift(UUID uuid, UpdateGiftDTO updateDTO) {
        return with()
                .body(updateDTO)
                .contentType(JSON)
                .put("/api/rewards/gifts/{uuid}", uuid);
    }

    private GiftDTO readGiftSuccessfully(UUID uuid) {
        return readGift(uuid)
                .then()
                .statusCode(OK.value())
                .extract()
                .as(GiftDTO.class);
    }

    private Response readGift(UUID uuid) {
        return when()
                .get("/api/rewards/gifts/{uuid}", uuid);
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