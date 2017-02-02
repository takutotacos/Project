package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Model;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by sugitatakuto on 2017/02/01.
 */
public class User {

    private String id;
    private String userId;
    private String email;
    private String password;
    private String passwordConfirmation;
    private Bitmap icon;
    private String iconFileType;
    private Date createdAt;
    private Date updatedAt;

    public User(String id, String userId, String email, String password, String passwordConfirmation,
                Bitmap icon, String iconFileType, Date createdAt, Date updatedAt) {
        this.id = id;
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.iconFileType = iconFileType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.icon = icon;
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getPasswordConfirmation() {
        return this.passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIconFileType() {
        return iconFileType;
    }

    public void setIconFileType(String iconFileType) {
        this.iconFileType = iconFileType;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
