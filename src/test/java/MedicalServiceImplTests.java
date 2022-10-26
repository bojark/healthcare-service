import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.medical.MedicalServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;


//    Проверить вывод сообщения во время проверки температуры checkTemperature
//    Проверить, что сообщения не выводятся, когда показатели в норме.

public class MedicalServiceImplTests {


    final static PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
    final SendAlertService alertService = Mockito.mock(SendAlertService.class);
    final MedicalServiceImpl service = new MedicalServiceImpl(patientInfoRepository, alertService);
    final static String patientId = "1";

    @BeforeAll
    public static void init() {
        Mockito.when(patientInfoRepository.getById(patientId)).thenReturn(new PatientInfo("name",
                "surname", LocalDate.now(), new HealthInfo(new BigDecimal("36.6"),
                new BloodPressure(15, 5))));
    }

    //    Проверить вывод сообщения во время проверки давления checkBloodPressure
    @Test
    public void checkBloodPressureTest() {
        BloodPressure bloodPressure = new BloodPressure(20, 17);
        service.checkBloodPressure(patientId, bloodPressure);
        Mockito.verify(alertService, Mockito.only()).send(Mockito.anyString());
    }

    //    Проверить, что сообщения не выводятся, когда показатели в норме.
    @Test
    public void checkBloodPressureNormalTest() {
        BloodPressure bloodPressure = new BloodPressure(15, 5);
        service.checkBloodPressure(patientId, bloodPressure);
        Mockito.verify(alertService, Mockito.never()).send(Mockito.anyString());
    }

    //    Проверить вывод сообщения во время проверки температуры checkTemperature
    //    TODO: Этот тест не проходит, не смог разобраться почему.
    @Test
    public void checkTemperatureTest() {
        BigDecimal temperature = new BigDecimal("39.6");
        service.checkTemperature(patientId, temperature);
        Mockito.verify(alertService, Mockito.only()).send(Mockito.anyString());

    }

    @Test
    public void checkTemperatureNormalTest() {
        BigDecimal temperature = new BigDecimal("36.6");
        service.checkTemperature(patientId, temperature);
        Mockito.verify(alertService, Mockito.never()).send(Mockito.anyString());

    }


}
