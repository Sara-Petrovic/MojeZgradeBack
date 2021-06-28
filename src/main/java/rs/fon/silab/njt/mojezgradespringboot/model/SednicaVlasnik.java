
package rs.fon.silab.njt.mojezgradespringboot.model;


public class SednicaVlasnik {

    private SednicaSkupstine sednicaSkupstine;
    private VlasnikPosebnogDela vlasnikPosebnogDela;

    public SednicaVlasnik() {
    }

    public SednicaVlasnik(SednicaSkupstine sednicaSkupstine, 
            VlasnikPosebnogDela vlasnikPosebnogDela) {
        this.sednicaSkupstine = sednicaSkupstine;
        this.vlasnikPosebnogDela = vlasnikPosebnogDela;
    }

    public SednicaSkupstine getSednicaSkupstine() {
        return sednicaSkupstine;
    }

    public void setSednicaSkupstine(SednicaSkupstine sednicaSkupstine) {
        this.sednicaSkupstine = sednicaSkupstine;
    }

    public VlasnikPosebnogDela getVlasnikPosebnogDela() {
        return vlasnikPosebnogDela;
    }

    public void setVlasnikPosebnogDela(VlasnikPosebnogDela vlasnikPosebnogDela) {
        this.vlasnikPosebnogDela = vlasnikPosebnogDela;
    }

//    @Override
//    public String getTableName() {
//        return "sednica_vlasnik";
//    }
//
//    @Override
//    public String getColumnNamesForInsert() {
//        return " sednicaskupstineid, vlasnikposebnogdelaid ";
//    }
//
//    @Override
//    public String getInsertValues() {
//        StringBuilder sb = new StringBuilder();
//        sb.append(sednicaSkupstine.getSednicaSkupstineId()).append(",")
//                .append("").append(vlasnikPosebnogDela.getVlasnikId()).append("");
//        System.out.println(sb.toString());
//        return sb.toString();
//    }
//
//    @Override
//    public String getAlijas() {
//        return " as sv ";
//    }
//
//    @Override
//    public void setId(Long id) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public String getUpdateValues() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public String getPrimaryKeyValue() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public String getJoin() {
//        return " inner join sednica_skupstine AS ss on (ss.sednicaskupstineid = sv.sednicaskupstineid) inner join vlasnik_posebnog_dela AS v on (sv.vlasnikposebnogdelaid = v.vlasnikid) ";
//    }
//
//    @Override
//    public String selectWhere() {
//        return " where ss.sednicaskupstineid=" + sednicaSkupstine.getSednicaSkupstineId();
//    }
//
//    @Override
//    public List<GenericEntity> makeList(ResultSet rs) throws Exception {
//        List<GenericEntity> lista = new ArrayList<>();
//        while (rs.next()) {
//            vlasnikPosebnogDela = new VlasnikPosebnogDela();
//            vlasnikPosebnogDela.setVlasnikId(rs.getLong("vlasnikid"));
//            vlasnikPosebnogDela.setIme(rs.getString("ime"));
//            vlasnikPosebnogDela.setPrezime(rs.getString("prezime"));
//            vlasnikPosebnogDela.setBrojPosebnogDela(rs.getString("brojposebnogdela"));
//            vlasnikPosebnogDela.setVelicinaPosebnogDela(rs.getDouble("velicinaposebnogdela"));
//            vlasnikPosebnogDela.setMernaJedinica((JedinicaMere.valueOf(rs.getString("mernajedinica"))));
//            vlasnikPosebnogDela.setKontaktVlasnika(rs.getString("kontaktvlasnika"));
//            
//            SednicaVlasnik sednicaVlasnik = new SednicaVlasnik();
//            sednicaVlasnik.setSednicaSkupstine(this.sednicaSkupstine);
//            sednicaVlasnik.setVlasnikPosebnogDela(this.vlasnikPosebnogDela);
//            lista.add(sednicaVlasnik);
//        }
//        //rs.close();
//        return lista;
//    }
}
