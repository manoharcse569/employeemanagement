# ReadMe

In case of any clarifications please reach me at 

+91 9052937930(call)
+91 9916826694(whatsapp)
manohargurram547@gmail.com(email)

Requirements:

1. load employees from CSV
2. Board wants to make sure that every manager earns at least 20% more than the average salary of its direct subordinates, but no more than 50% more than that average.
   - identify manager with reporting employees
   - identify average salary of reporting employees
   - identify 20% more than average salary of reporting employees
   - identify 50% more than average salary  of reporting employees
3. Company wants to avoid too long reporting lines, therefore we would like to identify all employees which have more than 4 managers between them and the CEO
   - identify employees with more than 5 managers, super managers including CEO
   
4. which managers earn less than they should, and by how much
5. which managers earn more than they should, and by how much
6. which employees have a reporting line which is too long, and by how much




EmployeeRepository.java:

	* which will parse employees.csv and load employees. in case of issue throws CSVParsingException
	
	
	
	
Note: 

* consider //System.out.println() equivalent to logger.debug() in production grade application
* consider System.out.println() equivalent to logger.info() in production grade application
* here in production grade applications we can use junit-jupiter, mockito using spring-boot-starter-test. But for as mentioned in the        documentation used junit for simplicity
* written additional test cases with modified values to cover more cases and to increase code coverage
