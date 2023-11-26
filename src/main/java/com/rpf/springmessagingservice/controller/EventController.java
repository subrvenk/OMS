package com.rpf.springmessagingservice.controller;

import com.rpf.springmessagingservice.OrderDetailsDto;
import com.rpf.springmessagingservice.service.MessageService;
import com.rpf.springmessagingservice.util.Utility;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class EventController {

    @GetMapping("/health")
    @ApiOperation(value = "health", nickname = "health checkup")
    public String health() {
        return "health check is fine";
    }

    @Autowired
    private MessageService messageService;
    @PostMapping("/publish")
    public List<ResponseEntity<?>> publishMessages(@RequestBody OrderDetailsDto orderDetailsDto) {
        List<ResponseEntity<?>> responseEntities = new ArrayList<>();
        for(int i =0; i < 10000; i++) {
            orderDetailsDto.setOrderId(orderDetailsDto.getOrderId() + i);
            orderDetailsDto.setQty(orderDetailsDto.getQty() + i);
            orderDetailsDto.setSku(orderDetailsDto.getSku() + i);
            responseEntities.add(publishMessage(orderDetailsDto));
        }
        return responseEntities;
    }

    private ResponseEntity<String> publishMessage(OrderDetailsDto orderDetailsDto) {
        ResponseEntity responseEntity = null;
        try {
            String json = Utility.convertObjectAsJson(orderDetailsDto);
            messageService.sendMessage(json);
            responseEntity = new ResponseEntity<>("Message published Successfully!!!", HttpStatusCode.valueOf(200));
        }
        catch (Exception exception) {
            responseEntity = new ResponseEntity<>("Message Failed, lets republish !!!", HttpStatus.EXPECTATION_FAILED);
        }
        return responseEntity;
    }

}
