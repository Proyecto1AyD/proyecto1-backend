package ayd.proyecto1.fastdelivery.controller.api;


import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/receipt")
public interface ReceiptApi {



    @GetMapping("/all")
    public ResponseEntity<ResponseSuccessfullyDto> getAllReceipt();

    @GetMapping("/{id}")
    public ResponseEntity<ResponseSuccessfullyDto> getReceiptById(@PathVariable(name = "id") Integer receiptId);


    @GetMapping("/delivery_person/{id}")
    public ResponseEntity<ResponseSuccessfullyDto> getAllReceiptsByDeliveryPerson(@PathVariable(name = "id") Integer deliveryPerosnId);

    @GetMapping("/delivery_assignment/{id}")
    public ResponseEntity<ResponseSuccessfullyDto> getAllReceiptsByAssignment(@PathVariable(name = "id") Integer deliveryOrderAssignmentId);

}
