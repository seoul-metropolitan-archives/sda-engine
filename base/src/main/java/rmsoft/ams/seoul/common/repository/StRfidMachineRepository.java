package rmsoft.ams.seoul.common.repository;


import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.StRfidMachine;

@Repository
public interface StRfidMachineRepository extends AXBootJPAQueryDSLRepository<StRfidMachine, StRfidMachine.StRfidMachineId> {
}
