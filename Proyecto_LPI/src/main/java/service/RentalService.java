package service;

import repository.IRentalRepository;

public class RentalService implements IRentalService {
    private final IRentalRepository rentalRepository;

    public RentalService(IRentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    
}