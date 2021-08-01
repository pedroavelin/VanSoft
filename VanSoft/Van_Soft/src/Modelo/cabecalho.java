/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Gonga
 */
public class cabecalho {
    // CABECALHO

    private String CompanyID, TaxRegistrationNumber, TaxAccountingBasis, CompanyName, BusinessName;
// Endereço da empresa
    private String AddressDetail, City, Province, Country;
    private String FiscalYear, StartDate, EndDate, DateCreated, CurrencyCode,
            TaxEntity, ProductCompanyTaxID, SoftwareValidationNumber, ProductID,
            ProductVersion, Telephone, Email, Website;
    // Fim cabeçalho

    public cabecalho(String CompanyID, String TaxRegistrationNumber, String TaxAccountingBasis, String CompanyName, String BusinessName, String AddressDetail, String City, String Province, String Country, String FiscalYear, String StartDate, String EndDate, String DateCreated, String CurrencyCode, String TaxEntity, String ProductCompanyTaxID, String SoftwareValidationNumber, String ProductID, String ProductVersion, String Telephone, String Email, String Website) {
        this.CompanyID = CompanyID;
        this.TaxRegistrationNumber = TaxRegistrationNumber;
        this.TaxAccountingBasis = TaxAccountingBasis;
        this.CompanyName = CompanyName;
        this.BusinessName = BusinessName;
        this.AddressDetail = AddressDetail;
        this.City = City;
        this.Province = Province;
        this.Country = Country;
        this.FiscalYear = FiscalYear;
        this.StartDate = StartDate;
        this.EndDate = EndDate;
        this.DateCreated = DateCreated;
        this.CurrencyCode = CurrencyCode;
        this.TaxEntity = TaxEntity;
        this.ProductCompanyTaxID = ProductCompanyTaxID;
        this.SoftwareValidationNumber = SoftwareValidationNumber;
        this.ProductID = ProductID;
        this.ProductVersion = ProductVersion;
        this.Telephone = Telephone;
        this.Email = Email;
        this.Website = Website;
    }

    public String getCompanyID() {
        return CompanyID;
    }

    public void setCompanyID(String CompanyID) {
        this.CompanyID = CompanyID;
    }

    public String getTaxRegistrationNumber() {
        return TaxRegistrationNumber;
    }

    public void setTaxRegistrationNumber(String TaxRegistrationNumber) {
        this.TaxRegistrationNumber = TaxRegistrationNumber;
    }

    public String getTaxAccountingBasis() {
        return TaxAccountingBasis;
    }

    public void setTaxAccountingBasis(String TaxAccountingBasis) {
        this.TaxAccountingBasis = TaxAccountingBasis;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String CompanyName) {
        this.CompanyName = CompanyName;
    }

    public String getBusinessName() {
        return BusinessName;
    }

    public void setBusinessName(String BusinessName) {
        this.BusinessName = BusinessName;
    }

    public String getAddressDetail() {
        return AddressDetail;
    }

    public void setAddressDetail(String AddressDetail) {
        this.AddressDetail = AddressDetail;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String Province) {
        this.Province = Province;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public String getFiscalYear() {
        return FiscalYear;
    }

    public void setFiscalYear(String FiscalYear) {
        this.FiscalYear = FiscalYear;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String StartDate) {
        this.StartDate = StartDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String EndDate) {
        this.EndDate = EndDate;
    }

    public String getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(String DateCreated) {
        this.DateCreated = DateCreated;
    }

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(String CurrencyCode) {
        this.CurrencyCode = CurrencyCode;
    }

    public String getTaxEntity() {
        return TaxEntity;
    }

    public void setTaxEntity(String TaxEntity) {
        this.TaxEntity = TaxEntity;
    }

    public String getProductCompanyTaxID() {
        return ProductCompanyTaxID;
    }

    public void setProductCompanyTaxID(String ProductCompanyTaxID) {
        this.ProductCompanyTaxID = ProductCompanyTaxID;
    }

    public String getSoftwareValidationNumber() {
        return SoftwareValidationNumber;
    }

    public void setSoftwareValidationNumber(String SoftwareValidationNumber) {
        this.SoftwareValidationNumber = SoftwareValidationNumber;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String ProductID) {
        this.ProductID = ProductID;
    }

    public String getProductVersion() {
        return ProductVersion;
    }

    public void setProductVersion(String ProductVersion) {
        this.ProductVersion = ProductVersion;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String Telephone) {
        this.Telephone = Telephone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String Website) {
        this.Website = Website;
    }
    
    
    
}
