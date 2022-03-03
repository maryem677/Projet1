package model;

public class Stagiaire {
    private String nom;
    private String prenom;
    private String departement;
    private String promo;
    private int annee;
    public static final int TAILLE_MAX_NOM_PRENOM = 30;
    public static final int TAILLE_MAX_DEP_PROMO = 10;
    
    
    public Stagiaire(String nom, String prenom, String departement, String promo, int annee) {
        super();
        this.nom = nom;
        this.prenom = prenom;
        this.departement = departement;
        this.promo = promo;
        this.annee = annee;
    }
    
    
    
    public Stagiaire() {
		super();
	}



	public String nomLong() {
    String nomLong = this.nom;
            for(int i = this.nom.length(); i < TAILLE_MAX_NOM_PRENOM; i++) {
                nomLong += " ";
                }
    return nomLong;
}
    public String prenomLong() {
    String prenomLong = this.prenom;
            for(int i = this.prenom.length(); i < TAILLE_MAX_NOM_PRENOM; i++) {
                prenomLong += " ";
                }
    return prenomLong;
}
    public String departementLong() {
        String departementLong = this.departement;
                for(int i = this.departement.length(); i < TAILLE_MAX_DEP_PROMO; i++) {
                    departementLong += " ";
                    }
        return departementLong;
    }
    
    public String promoLong() {
        String promoLong = this.promo;
                for(int i = this.promo.length(); i < TAILLE_MAX_DEP_PROMO; i++) {
                    promoLong += " ";
                    }
        return promoLong;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getDepartement() {
        return departement;
    }
    public void setDepartement(String departement) {
        this.departement = departement;
    }
    public String getPromo() {
        return promo;
    }
    public void setPromo(String promo) {
        this.promo = promo;
    }
	public int getAnnee() {
		return annee;
	}
	public void setAnnee(int annee) {
		this.annee = annee;
	}
	@Override
	public String toString() {
		return "Stagiaire [nom=" + nom + ", prenom=" + prenom + ", departement=" + departement + ", promo=" + promo
				+ ", annee=" + annee + "]";
	}
    
    public Boolean compare(Stagiaire stagiaireAComparer) {
    	Boolean b = false;
    	if (this.nom.equals(stagiaireAComparer.nom) && this.prenom.equals(stagiaireAComparer.prenom) && this.promo.equals(stagiaireAComparer.promo) && this.annee==stagiaireAComparer.annee) {
    		b = true;
    	}
    	
    	return b;
    }
    
    public Boolean compareMultiCrit(Stagiaire stagiaireAComparer) {
    	Boolean b = true;
    	if ((!stagiaireAComparer.nom.equals("")) && (!this.nom.equalsIgnoreCase(stagiaireAComparer.nom))) {
    		b = false;
    	}
    	if ((!stagiaireAComparer.prenom.equals("")) && (!this.prenom.equalsIgnoreCase(stagiaireAComparer.prenom))) {
    		b = false;
    	}
    	if ((!stagiaireAComparer.departement.equals("")) && (!this.departement.equalsIgnoreCase(stagiaireAComparer.departement))) {
    		b = false;
    	}
    	if ((!stagiaireAComparer.promo.equals("")) && (!this.promo.equalsIgnoreCase(stagiaireAComparer.promo))) {
    		b = false;
    	}
    	if ((stagiaireAComparer.annee!=0) && (this.annee!=stagiaireAComparer.annee)) {
    		b = false;
    	}
    	return b;
    }

    
    
}