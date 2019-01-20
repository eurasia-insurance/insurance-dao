package test.dao;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Currency;

import com.lapsa.insurance.domain.CalculationData;
import com.lapsa.insurance.domain.ContactData;
import com.lapsa.insurance.domain.DriverLicenseData;
import com.lapsa.insurance.domain.IdentityCardData;
import com.lapsa.insurance.domain.OriginData;
import com.lapsa.insurance.domain.PaymentData;
import com.lapsa.insurance.domain.PersonalData;
import com.lapsa.insurance.domain.RequesterData;
import com.lapsa.insurance.domain.ResidenceData;
import com.lapsa.insurance.domain.VehicleCertificateData;
import com.lapsa.insurance.domain.policy.Policy;
import com.lapsa.insurance.domain.policy.PolicyDriver;
import com.lapsa.insurance.domain.policy.PolicyRequest;
import com.lapsa.insurance.domain.policy.PolicyVehicle;
import com.lapsa.insurance.elements.IdentityCardType;
import com.lapsa.insurance.elements.InsuranceClassType;
import com.lapsa.insurance.elements.InsuredAgeClass;
import com.lapsa.insurance.elements.InsuredExpirienceClass;
import com.lapsa.insurance.elements.Sex;
import com.lapsa.insurance.elements.VehicleAgeClass;
import com.lapsa.insurance.elements.VehicleClass;
import com.lapsa.international.country.Country;
import com.lapsa.international.localization.LocalizationLanguage;
import com.lapsa.international.phone.CountryCode;
import com.lapsa.international.phone.PhoneNumber;
import com.lapsa.kz.country.KZArea;
import com.lapsa.kz.country.KZCity;

import tech.lapsa.kz.taxpayer.TaxpayerNumber;
import tech.lapsa.kz.vehicle.VehicleRegNumber;

public class TestObjectsCreatorHelper {

    private static final String LAPSA_TEST_GMAIL_COM = "lapsa.test@gmail.com";

    public static PolicyRequest generatePolicyRequest() {
	final PolicyRequest request = new PolicyRequest();
	request.setCreated(Instant.now());

	{
	    final Policy policy = request.getPolicy();
	    {
		final CalculationData calculation = policy.getCalculation();
		calculation.setAmount(500d);
		calculation.setCurrency(Currency.getInstance("KZT"));
	    }
	    {
		{
		    final PolicyDriver driver = policy.addDriver(new PolicyDriver());
		    driver.setIdNumber(TaxpayerNumber.of("123123123127"));
		    driver.setHasAnyPrivilege(false);
		    driver.setAgeClass(InsuredAgeClass.OVER25);
		    driver.setExpirienceClass(InsuredExpirienceClass.MORE2);
		    driver.setInsuranceClassType(InsuranceClassType.CLASS_3);
		    {
			final ContactData contactData = driver.getContactData();
			contactData.setEmail(LAPSA_TEST_GMAIL_COM);
			contactData.setPhone(PhoneNumber.of(CountryCode.KZ, "701", "9377979"));
		    }
		    {
			final DriverLicenseData driverLicenseData = driver.getDriverLicenseData();
			driverLicenseData.setNumber("123123");
			driverLicenseData.setDateOfIssue(LocalDate.now());
		    }
		    {
			final IdentityCardData identityCardData = driver.getIdentityCardData();
			identityCardData.setType(IdentityCardType.ID_CARD);
			identityCardData.setDateOfIssue(LocalDate.now());
			identityCardData.setIssuingAuthority("МВД РК");
			identityCardData.setNumber("12321321");
		    }
		    {
			final OriginData originData = driver.getOriginData();
			originData.setCountry(Country.KAZ);
		    }
		    {
			final PersonalData personalData = driver.getPersonalData();
			personalData.setSurename("Исаев");
			personalData.setName("Вадим");
			personalData.setPatronymic("Олегович");
			personalData.setGender(Sex.MALE);
			personalData.setDateOfBirth(LocalDate.now());
		    }
		    {
			final ResidenceData residenceData = driver.getResidenceData();
			residenceData.setAddress("ул. Курмангазы, 104");
			residenceData.setCity(KZCity.ALM);
			residenceData.setResident(true);
		    }
		}

	    }
	    {
		{
		    final PolicyVehicle vehicle = policy.addVehicle(new PolicyVehicle());
		    vehicle.setCity(KZCity.ALM);
		    vehicle.setForcedMajorCity(true);
		    vehicle.setArea(KZArea.GALM);
		    vehicle.setVehicleAgeClass(VehicleAgeClass.OVER7);
		    vehicle.setVehicleClass(VehicleClass.CAR);

		    vehicle.setColor("черный");
		    vehicle.setManufacturer("Infiniti");
		    vehicle.setModel("QX70");
		    vehicle.setVinCode("QWEWQEWQEWQEWQ");
		    vehicle.setYearOfManufacture(2014);
		    {
			final VehicleCertificateData certificateData = vehicle.getCertificateData();
			certificateData.setDateOfIssue(LocalDate.now());
			certificateData.setNumber("123123");
			certificateData.setRegistrationNumber(VehicleRegNumber.of("237blm01"));
		    }

		}
	    }
	}

	{
	    final PaymentData payment = request.getPayment();
	    payment.setInvoiceNumber("921321321321");
	}

	{
	    final RequesterData requester = request.getRequester();
	    requester.setName("Исаев Вадим Олегович");
	    requester.setEmail(LAPSA_TEST_GMAIL_COM);
	    requester.setPhone(PhoneNumber.of(CountryCode.KZ, "701", "9377979"));
	    requester.setPreferLanguage(LocalizationLanguage.RUSSIAN);
	    requester.setAllowSpam(true);
	    requester.setAllowProcessPersonalData(true);
	}
	return request;
    }
}