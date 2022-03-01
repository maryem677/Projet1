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



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + annee;
		result = prime * result + ((departement == null) ? 0 : departement.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((prenom == null) ? 0 : prenom.hashCode());
		result = prime * result + ((promo == null) ? 0 : promo.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stagiaire other = (Stagiaire) obj;
		if (annee != other.annee)
			return false;
		if (departement == null) {
			if (other.departement != null)
				return false;
		} else if (!departement.equals(other.departement))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (prenom == null) {
			if (other.prenom != null)
				return false;
		} else if (!prenom.equals(other.prenom))
			return false;
		if (promo == null) {
			if (other.promo != null)
				return false;
		} else if (!promo.equals(other.promo))
			return false;
		return true;
	}
    
    

    
    
}