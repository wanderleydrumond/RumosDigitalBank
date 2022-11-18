package pt.drumond.rumosdigitalbank.model;

import java.time.LocalDate;

/**
 * Entity class Customer.
 */
public class Customer {
    private String nif, name, password, phone, mobile, email, profession;
    private LocalDate birthDate;

    public Customer() {
    }

    public Customer(String nif, String name, String password, String phone, String mobile, String email, String profession, LocalDate birthDate) {
        this.nif = nif;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.mobile = mobile;
        this.email = email;
        this.profession = profession;
        this.birthDate = birthDate;
    }

    public String getNif() {
        return nif;
    }

    /**
     * Sets a NIF number if it has 9 digits and the first one is different that zero.
     *
     * @see <a href="https://phcgo.net/blog/nif-por-3-vai-ser-realidade/">NIF começado por 3 vai ser realidade em breve</a>
     * @param nif the NIF number
     * @return
     * <ul>
     *     <li>true if NIF is set</li>
     *     <li>false if NIF is not set</li>
     * </ul>
     */
    public boolean setNif(String nif) {
        if (nif.matches("^[1-9][0-9]{8}$")) {
            this.nif = nif;
        } else {
            System.out.println("Invalid nif number");
            return false;
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    /**
     * Sets the given phone number if it has 9 digits which the first must be equal two or three.
     *
     * @param phone the phone number
     * @return
     * <ul>
     *     <li>true if NIF is set</li>
     *     <li>false if NIF is not set</li>
     * </ul>
     */
    public boolean setPhone(String phone) {
        if (phone.matches("^[2-3][0-9]{8}$")) {
            this.phone = phone;
        } else {
            System.out.println("Invalid mobile number");
            return false;
        }
        return true;
    }

    public String getMobile() {
        return mobile;
    }

    /**
     * Sets given mobile phone number if it has 9 digits and the first one is equalor three.
     *
     * @param mobile the mobile phone number
     * @return
     * <ul>
     *     <li>true if NIF is set</li>
     *     <li>false if NIF is not set</li>
     * </ul>
     */
    public boolean setMobile(String mobile) {
        if (mobile.matches("^(9)[0-9]{8}$")) {
            this.mobile = mobile;
        } else {
            System.out.println("Invalid mobile number");
            return false;

        }
        return true;
    }

    public String getEmail() {
        return email;
    }

    /**
     * Sets the given e-mail if it has something before and after the @.
     *
     * @param email the e-mail
     * @return
     * <ul>
     *     <li>true if NIF is set</li>
     *     <li>false if NIF is not set</li>
     * </ul>
     */
    public boolean setEmail(String email) {
        if (email.matches("^(.+)@(.+)$")) {
            this.email = email;
        } else {
            System.out.println("Invalid e-mail");
            return false;
        }
        return true;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return  "| NIF: " + nif +
                " | NAME: " + name +
                " | PASSWORD: " + password +
                " | PHONE: " + phone +
                " | MOBILE: " + mobile +
                " | E-MAIL: " + email +
                " | PROFESSION: " + profession +
                " | BIRTHDATE: " + birthDate +
                " |";
    }
}