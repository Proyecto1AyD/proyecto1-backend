package ayd.proyecto1.fastdelivery.controller;

import ayd.proyecto1.fastdelivery.controller.api.DeliveryOrderApi;
import ayd.proyecto1.fastdelivery.dto.request.NewDeliveryOrderDto;
import ayd.proyecto1.fastdelivery.dto.response.DeliveryOrderDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.service.DeliveryOrderService;
import ayd.proyecto1.fastdelivery.service.DeliveryOrderUpdatesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class DeliveryOrderController implements DeliveryOrderApi {

    private final DeliveryOrderService deliveryOrderService;

    private final DeliveryOrderUpdatesService deliveryOrderUpdatesService;

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> createDeliveryOrder(NewDeliveryOrderDto newDeliveryOrderDto) {
        log.info("POST deliveryOrder/create");
        ResponseSuccessfullyDto response = deliveryOrderService.createDeliveryOrder(newDeliveryOrderDto);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getDeliveryOrderById(Integer id) {
        log.info("GET deliveryOrder/{}", id);
        ResponseSuccessfullyDto response = deliveryOrderService.getDeliveryOrderById(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getDeliveryOrderByIdBusiness(Integer idBusiness) {
        log.info("GET deliveryOrder/business/{}", idBusiness);
        ResponseSuccessfullyDto response = deliveryOrderService.getDeliveryOrderByIdBusiness(idBusiness);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getDeliveryOrderByIdDeliveryOrderStatus(Integer idDeliveryOrderStatus) {
        log.info("GET deliveryOrder/deliveryOrderStatus/{}", idDeliveryOrderStatus);
        ResponseSuccessfullyDto response = deliveryOrderService.getDeliveryOrderByIdDeliveryOrderStatus(idDeliveryOrderStatus);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllDeliveryOrder() {
        log.info("GET deliveryOrder/all");
        ResponseSuccessfullyDto response = deliveryOrderService.getAllDeliveryOrder();
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> updateDeliveryOrder(DeliveryOrderDto deliveryOrderDto) {
        log.info("PUT deliveryOrder/update");
        ResponseSuccessfullyDto response = deliveryOrderService.updateDeliveryOrder(deliveryOrderDto, false);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> updateDeliveryOrderRestricted(DeliveryOrderDto deliveryOrderDto) {
        log.info("PUT deliveryOrder/update/restricted");
        ResponseSuccessfullyDto response = deliveryOrderService.updateDeliveryOrder(deliveryOrderDto, true);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> deleteDeliveryOrder(Integer id) {
        log.info("DELETE deliveryOrder/delete/{}", id);
        ResponseSuccessfullyDto response = deliveryOrderService.deleteCard(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> deliveredDeliveryOrder(Integer id) {
        log.info("GET deliveryOrder/delivered/{}", id);
        ResponseSuccessfullyDto response = deliveryOrderUpdatesService.deliveredDeliveryOrder(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
