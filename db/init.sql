alter user HR account unlock;
alter user HR identified by scott;

CREATE OR REPLACE PROCEDURE HR.p_insert_dept(
       p_dept_id OUT HR.departments.department_id%TYPE,
       p_dept_name IN HR.departments.department_name%TYPE,
       p_manager_id IN HR.departments.manager_id%TYPE,
       p_location_id IN HR.departments.location_id%TYPE)
IS
BEGIN

  INSERT INTO HR.departments (department_id, department_name, manager_id, location_id) 
  VALUES (HR.departments_seq.NEXTVAL, p_dept_name,p_manager_id, p_location_id);
  
  SELECT HR.departments_seq.CURRVAL INTO p_dept_id FROM DUAL;
  COMMIT;
END;