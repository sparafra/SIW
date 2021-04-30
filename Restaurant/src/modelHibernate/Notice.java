package modelHibernate;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.json.JSONArray;
import org.json.JSONObject;

@Entity
@Table(name="notice")
public class Notice {
	@Id
	@GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
      name = "sequence-generator",
      strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
      parameters = {
        @Parameter(name = "sequence_name", value = "notice_sequence"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")
        }
    )	
	Long id;
	
	boolean state;
	String created_by;
	String message;
	String received_by;
	String type;
	String title;

	public Notice() {}
	
	

	public Notice(boolean state, String created_by, String message, String received_by, String type, String title) {
		super();
		this.state = state;
		this.created_by = created_by;
		this.message = message;
		this.received_by = received_by;
		this.type = type;
		this.title = title;
	}


	//Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getReceived_by() {
		return received_by;
	}

	public void setReceived_by(String received_by) {
		this.received_by = received_by;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public JSONObject getJson()
	{
		JSONObject obj = new JSONObject();

		obj.put("id", id);
		obj.put("state", state);
		obj.put("created_by", created_by);
		obj.put("message", message);
		obj.put("received_by", received_by);
		obj.put("type", type);
		obj.put("title", title);
		
		return obj;
	}
	
}
