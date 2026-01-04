package com.emissiontracker.emission_tracker.emission;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class TransportEmission {

    private double totalTransportEmission;

    private double emissionByPlane;
    private double emissionByBus;
    private double emissionByCar;
    private double emissionByBike;

    public void total(){
       totalTransportEmission = emissionByPlane + emissionByBus +
                emissionByCar + emissionByBike;
    }

}
