package rs.fon.silab.njt.mojezgradespringboot.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SednicaSkupstine  {

    private Long sednicaSkupstineId;
    private Date datumOdrzavanja;
    private int brojPrisutnih;
    private String dnevniRed;
    private StambenaZajednica stambenaZajednica;
    private List<VlasnikPosebnogDela> vlasnici;

    public SednicaSkupstine() {
        vlasnici = new ArrayList<>();
    }

    public SednicaSkupstine(Long sednicaSkupstineId, Date datumOdrzavanja, 
            int brojPrisutnih, String dnevniRed, StambenaZajednica stambenaZajednica,
            List<VlasnikPosebnogDela> vlasnici) {
        this.sednicaSkupstineId = sednicaSkupstineId;
        this.datumOdrzavanja = datumOdrzavanja;
        this.brojPrisutnih = brojPrisutnih;
        this.dnevniRed = dnevniRed;
        this.stambenaZajednica = stambenaZajednica;
        this.vlasnici = vlasnici;
    }

    public List<VlasnikPosebnogDela> getVlasnici() {
        return vlasnici;
    }

    public void setVlasnici(List<VlasnikPosebnogDela> vlasnici) {
        this.vlasnici = vlasnici;
    }

    public Long getSednicaSkupstineId() {
        return sednicaSkupstineId;
    }

    public void setSednicaSkupstineId(Long sednicaSkupstineId) {
        this.sednicaSkupstineId = sednicaSkupstineId;
    }

    public Date getDatumOdrzavanja() {
        return datumOdrzavanja;
    }

    public void setDatumOdrzavanja(Date datumOdrzavanja) {
        this.datumOdrzavanja = datumOdrzavanja;
    }

    public int getBrojPrisutnih() {
        return brojPrisutnih;
    }

    public void setBrojPrisutnih(int brojPrisutnih) {
        this.brojPrisutnih = brojPrisutnih;
    }

    public String getDnevniRed() {
        return dnevniRed;
    }

    public void setDnevniRed(String dnevniRed) {
        this.dnevniRed = dnevniRed;
    }

    public StambenaZajednica getStambenaZajednica() {
        return stambenaZajednica;
    }

    public void setStambenaZajednica(StambenaZajednica stambenaZajednica) {
        this.stambenaZajednica = stambenaZajednica;
    }

//    @Override
//    public String getTableName() {
//        return "sednica_skupstine";
//    }
//
//    @Override
//    public String getColumnNamesForInsert() {
//        return "datumodrzavanja, brojprisutnih, dnevnired, stambenazajednicaid";
//    }
//
//    @Override
//    public String getInsertValues() {
//        StringBuilder sb = new StringBuilder();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
//        sb.append("'").append(format.format(datumOdrzavanja)).append("',")
//                .append(brojPrisutnih).append(",")
//                .append("'").append(dnevniRed).append("',")
//                .append("").append(stambenaZajednica.getStambenaZajednicaId()).append("");
//        System.out.println(sb.toString());
//        return sb.toString();
//    }
//
//    @Override
//    public String getAlijas() {
//        return " as ss";
//    }
//
//    @Override
//    public void setId(Long id) {
//        this.sednicaSkupstineId = id;
//    }
//
//    @Override
//    public String getUpdateValues() {
//        StringBuilder sb = new StringBuilder();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
//        sb.append(" datumodrzavanja = '").append(format.format(datumOdrzavanja)).append("',")
//                .append(" brojprisutnih = ").append(brojPrisutnih).append(",")
//                .append(" dnevnired = ").append("'").append(dnevniRed).append("',")
//                .append(" stambenazajednicaid = ").append(stambenaZajednica.getStambenaZajednicaId()).append("");
//        System.out.println(sb.toString());
//        return sb.toString();
//    }
//
//    @Override
//    public String getPrimaryKeyValue() {
//        return " sednicaskupstineid = " + sednicaSkupstineId;
//    }
//
//    @Override
//    public String getJoin() {
//        return " inner join stambena_zajednica AS s on (s.stambenazajednicaid = ss.stambenazajednicaid) ";
//    }
//
//    @Override
//    public String selectWhere() {
//        if (stambenaZajednica != null) {
//            return " where s.stambenazajednicaid = " + stambenaZajednica.getStambenaZajednicaId();
//        }
//        return "";
//    }
//
//    @Override
//    public List<GenericEntity> makeList(ResultSet rs) throws Exception {
//        List<GenericEntity> lista = new ArrayList<>();
//        while (rs.next()) {
//            SednicaSkupstine sednica = new SednicaSkupstine();
//            sednica.setSednicaSkupstineId(rs.getLong("sednicaskupstineid"));
//            sednica.setDatumOdrzavanja(rs.getDate("datumodrzavanja"));
//            sednica.setBrojPrisutnih(rs.getInt("brojprisutnih"));
//            sednica.setDnevniRed(rs.getString("dnevnired"));
//
//            StambenaZajednica stambenaZajednica = new StambenaZajednica();
//            stambenaZajednica.setStambenaZajednicaId(rs.getLong("s.stambenazajednicaid"));
//
//            sednica.setStambenaZajednica(stambenaZajednica);
//            lista.add(sednica);
//        }
//
//        //rs.close();
//        return lista;
//    }
}
