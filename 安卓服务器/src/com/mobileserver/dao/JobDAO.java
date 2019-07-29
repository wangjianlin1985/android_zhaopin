package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Job;
import com.mobileserver.util.DB;

public class JobDAO {

	public List<Job> QueryJob(String qiyeObj,String positionName,int jobTypeObj,int specialObj,String city,int schoolRecordObj) {
		List<Job> jobList = new ArrayList<Job>();
		DB db = new DB();
		String sql = "select * from Job where 1=1";
		if (!qiyeObj.equals(""))
			sql += " and qiyeObj = '" + qiyeObj + "'";
		if (!positionName.equals(""))
			sql += " and positionName like '%" + positionName + "%'";
		if (jobTypeObj != 0)
			sql += " and jobTypeObj=" + jobTypeObj;
		if (specialObj != 0)
			sql += " and specialObj=" + specialObj;
		if (!city.equals(""))
			sql += " and city like '%" + city + "%'";
		if (schoolRecordObj != 0)
			sql += " and schoolRecordObj=" + schoolRecordObj;
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Job job = new Job();
				job.setJobId(rs.getInt("jobId"));
				job.setQiyeObj(rs.getString("qiyeObj"));
				job.setPositionName(rs.getString("positionName"));
				job.setJobTypeObj(rs.getInt("jobTypeObj"));
				job.setSpecialObj(rs.getInt("specialObj"));
				job.setPersonNum(rs.getString("personNum"));
				job.setCity(rs.getString("city"));
				job.setSalary(rs.getString("salary"));
				job.setSchoolRecordObj(rs.getInt("schoolRecordObj"));
				job.setWorkYears(rs.getString("workYears"));
				job.setWorkAddress(rs.getString("workAddress"));
				job.setWelfare(rs.getString("welfare"));
				job.setPositionDesc(rs.getString("positionDesc"));
				job.setConnectPerson(rs.getString("connectPerson"));
				job.setTelephone(rs.getString("telephone"));
				jobList.add(job);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return jobList;
	}
	/* 传入职位对象，进行职位的添加业务 */
	public String AddJob(Job job) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新职位 */
			String sqlString = "insert into Job(qiyeObj,positionName,jobTypeObj,specialObj,personNum,city,salary,schoolRecordObj,workYears,workAddress,welfare,positionDesc,connectPerson,telephone) values (";
			sqlString += "'" + job.getQiyeObj() + "',";
			sqlString += "'" + job.getPositionName() + "',";
			sqlString += job.getJobTypeObj() + ",";
			sqlString += job.getSpecialObj() + ",";
			sqlString += "'" + job.getPersonNum() + "',";
			sqlString += "'" + job.getCity() + "',";
			sqlString += "'" + job.getSalary() + "',";
			sqlString += job.getSchoolRecordObj() + ",";
			sqlString += "'" + job.getWorkYears() + "',";
			sqlString += "'" + job.getWorkAddress() + "',";
			sqlString += "'" + job.getWelfare() + "',";
			sqlString += "'" + job.getPositionDesc() + "',";
			sqlString += "'" + job.getConnectPerson() + "',";
			sqlString += "'" + job.getTelephone() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "职位添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "职位添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除职位 */
	public String DeleteJob(int jobId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Job where jobId=" + jobId;
			db.executeUpdate(sqlString);
			result = "职位删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "职位删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据职位id获取到职位 */
	public Job GetJob(int jobId) {
		Job job = null;
		DB db = new DB();
		String sql = "select * from Job where jobId=" + jobId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				job = new Job();
				job.setJobId(rs.getInt("jobId"));
				job.setQiyeObj(rs.getString("qiyeObj"));
				job.setPositionName(rs.getString("positionName"));
				job.setJobTypeObj(rs.getInt("jobTypeObj"));
				job.setSpecialObj(rs.getInt("specialObj"));
				job.setPersonNum(rs.getString("personNum"));
				job.setCity(rs.getString("city"));
				job.setSalary(rs.getString("salary"));
				job.setSchoolRecordObj(rs.getInt("schoolRecordObj"));
				job.setWorkYears(rs.getString("workYears"));
				job.setWorkAddress(rs.getString("workAddress"));
				job.setWelfare(rs.getString("welfare"));
				job.setPositionDesc(rs.getString("positionDesc"));
				job.setConnectPerson(rs.getString("connectPerson"));
				job.setTelephone(rs.getString("telephone"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return job;
	}
	/* 更新职位 */
	public String UpdateJob(Job job) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Job set ";
			sql += "qiyeObj='" + job.getQiyeObj() + "',";
			sql += "positionName='" + job.getPositionName() + "',";
			sql += "jobTypeObj=" + job.getJobTypeObj() + ",";
			sql += "specialObj=" + job.getSpecialObj() + ",";
			sql += "personNum='" + job.getPersonNum() + "',";
			sql += "city='" + job.getCity() + "',";
			sql += "salary='" + job.getSalary() + "',";
			sql += "schoolRecordObj=" + job.getSchoolRecordObj() + ",";
			sql += "workYears='" + job.getWorkYears() + "',";
			sql += "workAddress='" + job.getWorkAddress() + "',";
			sql += "welfare='" + job.getWelfare() + "',";
			sql += "positionDesc='" + job.getPositionDesc() + "',";
			sql += "connectPerson='" + job.getConnectPerson() + "',";
			sql += "telephone='" + job.getTelephone() + "'";
			sql += " where jobId=" + job.getJobId();
			db.executeUpdate(sql);
			result = "职位更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "职位更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
