package modelHibernate;

import java.util.Date;
import java.util.List;
import javax.persistence.*;  

//@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

@MappedSuperclass
public abstract class Review {

	
	@ManyToOne
	@MapsId("telephone")
	@JoinColumn(name = "telephone")
	User user;
	
	int vote;
		
	Date date_time;
	
    public Review(User user, int vote, Date date_time)
    {
        this.user = user;
        this.vote = vote;
        this.date_time = date_time;
    }
   
    public Review()
    {
    	
    }
    
    
    //Getters and Setters
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getVote() {
		return vote;
	}

	public void setVote(int vote) {
		this.vote = vote;
	}

	public Date getDate_time() {
		return date_time;
	}

	public void setDate_time(Date date_time) {
		this.date_time = date_time;
	}
    
    
    
}
    