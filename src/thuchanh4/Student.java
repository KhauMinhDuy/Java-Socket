package thuchanh4;

public class Student {

    private int id;
    private String hoTen;
    private String MaSV;
    private String IdLop;
    private float dbt1;
    private float dbt2;
    private float dbt3;
    private float dgk;


    public Student() {
    }

    public Student(String hoTen, String maSV, String idLop, float dbt1, float dbt2, float dbt3) {
        this.hoTen = hoTen;
        MaSV = maSV;
        IdLop = idLop;
        this.dbt1 = dbt1;
        this.dbt2 = dbt2;
        this.dbt3 = dbt3;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getMaSV() {
        return MaSV;
    }

    public void setMaSV(String maSV) {
        MaSV = maSV;
    }

    public String getIdLop() {
        return IdLop;
    }

    public void setIdLop(String idLop) {
        IdLop = idLop;
    }

    public float getDbt1() {
        return dbt1;
    }

    public void setDbt1(float dbt1) {
        this.dbt1 = dbt1;
    }

    public float getDbt2() {
        return dbt2;
    }

    public void setDbt2(float dbt2) {
        this.dbt2 = dbt2;
    }

    public float getDbt3() {
        return dbt3;
    }

    public void setDbt3(float dbt3) {
        this.dbt3 = dbt3;
    }

    public float getDgk() {
        return dgk;
    }

    public void setDgk(float dgk) {
        this.dgk = dgk;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", hoTen='" + hoTen + '\'' +
                ", MaSV='" + MaSV + '\'' +
                ", IdLop='" + IdLop + '\'' +
                ", dbt1=" + dbt1 +
                ", dbt2=" + dbt2 +
                ", dbt3=" + dbt3 +
                ", dgk=" + dgk +
                '}';
    }
}
