package main.NetworkingBasics.ResultStructure;

import java.sql.Timestamp;

public class SubItem_01 {
    String name;
    String id;
    String start_date;
    String oper_name;
    String reg_no;
    String credit_no;

    public String getCredit_no() {
        return credit_no;
    }

    public void setCredit_no(String credit_no) {
        this.credit_no = credit_no;
    }

    public String getReg_no() {
        return reg_no;
    }

    public void setReg_no(String reg_no) {
        this.reg_no = reg_no;
    }

    public String getOper_name() {
        return oper_name;
    }

    public void setOper_name(String oper_name) {
        this.oper_name = oper_name;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SubItem_01{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", start_date='" + start_date + '\'' +
                ", oper_name='" + oper_name + '\'' +
                ", reg_no='" + reg_no + '\'' +
                ", credit_no='" + credit_no + '\'' +
                '}';
    }
}
