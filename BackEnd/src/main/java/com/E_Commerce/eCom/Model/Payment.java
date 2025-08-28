package com.E_Commerce.eCom.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @OneToOne(mappedBy = "payment" , cascade = {CascadeType.MERGE,CascadeType.MERGE})
    private Order order;

    @NotBlank
    @Size(min = 4 , message = "Method name must be at least 4 letters")
    private String paymentMethod;

    private String pgPaymentId;
    private String pgPaymentStatus;
    private String pgResponseMessage;
    private String pgName;

    public Payment(String pgPaymentId, String pgPaymentStatus, String pgResponseMessage, String pgName, String paymentMethod) {
        this.pgPaymentId = pgPaymentId;
        this.pgPaymentStatus = pgPaymentStatus;
        this.pgResponseMessage = pgResponseMessage;
        this.pgName = pgName;
        this.paymentMethod = paymentMethod;
    }
}
