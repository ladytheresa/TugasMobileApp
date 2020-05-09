package umn.ac.tugasmobile;

public class User {
    private String displayname;
    private String namalengkap;
    private String nim;
    private String angkatan;
    private String pekerjaan;
    private String statuspekerjaan;
    private String nomorhp;
    private String profilepic;
    private String uid;
    private String email;
    private String domisili;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String displayname, String namalengkap, String nim, String angkatan, String pekerjaan, String statuspekerjaan, String nomorhp, String uid, String profilepic,
                String email, String domisili) {
        this.displayname = displayname;
        this.namalengkap = namalengkap;
        this.nim = nim;
        this.angkatan = angkatan;
        this.pekerjaan = pekerjaan;
        this.statuspekerjaan = statuspekerjaan;
        this.nomorhp=nomorhp;
        this.uid=uid;
        this.profilepic=profilepic;
        this.email=email;
        this.domisili=domisili;
    }

    public void setDisplayName(String displayname) {this.displayname = displayname;}
    public void setNim(String nim) {this.nim = nim;}
    public void setAngkatan(String angkatan) {this.angkatan=angkatan;}
    public void setPekerjaan(String pekerjaan) {this.pekerjaan=pekerjaan;}
    public void setStatusPekerjaan(String statuspekerjaan) {this.statuspekerjaan=statuspekerjaan;}
    public void setNomorhp(String nomorhp) {this.nomorhp=nomorhp;}
    public void setProfilePic(String profilepic) {this.profilepic = profilepic;}
    public void setUid(String uid) { this.uid = uid; }
    public void setEmail(String email) { this.email = email; }
    public void setNamalengkap(String namalengkap) { this.namalengkap = namalengkap; }
    public void setDomisili(String domisili) { this.domisili = domisili; }

    public String getDisplayName() {return this.displayname ;}
    public String getNim() {return this.nim ;}
    public String getAngkatan() {return this.angkatan;}
    public String getPekerjaan() { return this.pekerjaan;}
    public String getStatusPekerjaan() {return this.statuspekerjaan;}
    public String getNomorhp() {return this.nomorhp;}
    public String getProfilePic(){return this.profilepic;}
    public String getUid() { return uid; }
    public String getNamalengkap() { return namalengkap; }
    public String getEmail() { return email; }
    public String getDomisili() { return domisili; }
}

