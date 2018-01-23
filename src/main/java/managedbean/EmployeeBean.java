package managedbean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;

import com.onek.model.Department;
import com.onek.model.Employee;

@ManagedBean(name = "empBean")
public class EmployeeBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Employee employee = new Employee();

	public Employee getEmployee() {
		employee.setEmpId(1L);
		employee.setDepartment(new Department());
		employee.setFirstName("Onek");
		employee.setLastName("Team");
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
