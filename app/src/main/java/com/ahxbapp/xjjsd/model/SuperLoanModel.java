package com.ahxbapp.xjjsd.model;

public class SuperLoanModel {
//
//    /**
//     * ID : 44
//     * Name : 招财猫
//     * Logo : 8a88a5b2-850d-443b-a278-debca997fd7c.png
//     * Describe : aaaaaaaaaaaaaaaaa
//     * Interest : 0.1%
//     * Quota : 1000-10000
//     * Downloads : 1234
//     * Url : https://www.baidu.com/
//     * IsDel : 1
//     * Status : null
//     * Sort : 8
//     * Image1 : 0
//     * Image2 : 0
//     * Image3 : 0
//     * IsApp : 1
//     */
//
//    private int ID;
//    private String Name;
//    private String Logo;
//    private String Describe;
//    private String Interest;
//    private String Quota;
//    private int Downloads;
//    private String Url;
//    private int IsDel;
//    private Object Status;
//    private int Sort;
//    private String Image1;
//    private String Image2;
//    private String Image3;
//    private String Term;
//    private int IsApp;
//
//    public String getTerm() {
//        return Term;
//    }
//
//    public void setTerm(String term) {
//        Term = term;
//    }
//
//    public int getID() {
//        return ID;
//    }
//
//    public void setID(int ID) {
//        this.ID = ID;
//    }
//
//    public String getName() {
//        return Name;
//    }
//
//    public void setName(String Name) {
//        this.Name = Name;
//    }
//
//    public String getLogo() {
//        return Logo;
//    }
//
//    public void setLogo(String Logo) {
//        this.Logo = Logo;
//    }
//
//    public String getDescribe() {
//        return Describe;
//    }
//
//    public void setDescribe(String Describe) {
//        this.Describe = Describe;
//    }
//
//    public String getInterest() {
//        return Interest;
//    }
//
//    public void setInterest(String Interest) {
//        this.Interest = Interest;
//    }
//
//    public String getQuota() {
//        return Quota;
//    }
//
//    public void setQuota(String Quota) {
//        this.Quota = Quota;
//    }
//
//    public int getDownloads() {
//        return Downloads;
//    }
//
//    public void setDownloads(int Downloads) {
//        this.Downloads = Downloads;
//    }
//
//    public String getUrl() {
//        return Url;
//    }
//
//    public void setUrl(String Url) {
//        this.Url = Url;
//    }
//
//    public int getIsDel() {
//        return IsDel;
//    }
//
//    public void setIsDel(int IsDel) {
//        this.IsDel = IsDel;
//    }
//
//    public Object getStatus() {
//        return Status;
//    }
//
//    public void setStatus(Object Status) {
//        this.Status = Status;
//    }
//
//    public int getSort() {
//        return Sort;
//    }
//
//    public void setSort(int Sort) {
//        this.Sort = Sort;
//    }
//
//    public String getImage1() {
//        return Image1;
//    }
//
//    public void setImage1(String Image1) {
//        this.Image1 = Image1;
//    }
//
//    public String getImage2() {
//        return Image2;
//    }
//
//    public void setImage2(String Image2) {
//        this.Image2 = Image2;
//    }
//
//    public String getImage3() {
//        return Image3;
//    }
//
//    public void setImage3(String Image3) {
//        this.Image3 = Image3;
//    }
//
//    public int getIsApp() {
//        return IsApp;
//    }
//
//    public void setIsApp(int IsApp) {
//        this.IsApp = IsApp;
//    }

//    public static class DataBean {
    /**
     * logo : 8a88a5b2-850d-443b-a278-debca997fd7c.png
     * appName : 招财猫
     * loanId : 22
     * loanNum : 600.0
     * termId : 7
     * termNum : 0
     * loanlogID : 0
     * limitDay : 16
     * repayTime : null
     * repayLoan : 0
     * status : 8
     */
    private int Identifier;

    private String logo;
    private String appName;
    private int loanId;
    private double loanNum;
    private int termId;
    private int termNum;
    private int loanlogID;
    private int limitDay;
    private Object repayTime;
    private int repayLoan;
    private int status;
    private String wx;
    private double Interest;
    private double Userfees;
    private double Applyfees;

    public double getUserfees() {
        return Userfees;
    }

    public void setUserfees(double userfees) {
        Userfees = userfees;
    }

    public double getApplyfees() {
        return Applyfees;
    }

    public void setApplyfees(double applyfees) {
        Applyfees = applyfees;
    }

    public double getInterest() {
        return Interest;
    }

    public void setInterest(double interest) {
        Interest = interest;
    }


    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx;
    }

    public int getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(int identifier) {
        Identifier = identifier;
    }
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public double getLoanNum() {
        return loanNum;
    }

    public void setLoanNum(double loanNum) {
        this.loanNum = loanNum;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public int getTermNum() {
        return termNum;
    }

    public void setTermNum(int termNum) {
        this.termNum = termNum;
    }

    public int getLoanlogID() {
        return loanlogID;
    }

    public void setLoanlogID(int loanlogID) {
        this.loanlogID = loanlogID;
    }

    public int getLimitDay() {
        return limitDay;
    }

    public void setLimitDay(int limitDay) {
        this.limitDay = limitDay;
    }

    public Object getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(Object repayTime) {
        this.repayTime = repayTime;
    }

    public int getRepayLoan() {
        return repayLoan;
    }

    public void setRepayLoan(int repayLoan) {
        this.repayLoan = repayLoan;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
//}
