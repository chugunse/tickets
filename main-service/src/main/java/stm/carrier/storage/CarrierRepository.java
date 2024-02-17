package stm.carrier.storage;

import stm.carrier.model.Carrier;

import java.util.List;

public interface CarrierRepository {
    Carrier save(Carrier model);

    Carrier getById(int Id);

    void deleteById(int id);

    List<Carrier> getAll();

    Carrier update(Carrier carrier);

}
