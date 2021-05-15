package modelHibernate;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.json.JSONObject;

@Entity
@Table(name="analytic")

public class Analytic {
	
	@Id
	@GeneratedValue(generator = "analytic-sequence-generator")
    @GenericGenerator(
      name = "analytic-sequence-generator",
      strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
      parameters = {
        @Parameter(name = "sequence_name", value = "analytic_sequence"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")
        }
    )	
	Long id;
	String page;
	Date date_time;
	
	
	
	public Analytic()
	{
		
	}

	public Analytic(String page, Date date_time)
	{
		this.page = page;
		this.date_time = date_time;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public Date getDate_time() {
		return date_time;
	}

	public void setDate_time(Date date_time) {
		this.date_time = date_time;
	}

	public JSONObject getJson()
	{
		JSONObject obj = new JSONObject();

		obj.put("id", id);
		obj.put("page", page);
		obj.put("date_time", date_time);
		
		return obj;
	}
	
}
