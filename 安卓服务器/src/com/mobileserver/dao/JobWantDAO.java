package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.JobWant;
import com.mobileserver.util.DB;

public class JobWantDAO {

	public List<JobWant> QueryJobWant(int jobTypeObj,int specialObj,String positionName,String workCity,String userObj,String addTime) {
		List<JobWant> jobWantList = new ArrayList<JobWant>();
		DB db = new DB();
		String sql = "select * from JobWant where 1=1";
		if (jobTypeObj != 0)
			sql += " and jobTypeObj=" + jobTypeObj;
		if (specialObj != 0)
			sql += " and specialObj=" + specialObj;
		if (!positionName.equals(""))
			sql += " and positionName like '%" + positionName + "%'";
		if (!workCity.equals(""))
			sql += " and workCity like '%" + workCity + "%'";
		if (!userObj.equals(""))
			sql += " and userObj = '" + userObj + "'";
		if (!addTime.equals(""))
			sql += " and addTime like '%" + addTime + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				JobWant jobWant = new JobWant();
				jobWant.setWantId(rs.getInt("wantId"));
				jobWant.setJobTypeObj(rs.getInt("jobTypeObj"));
				jobWant.setSpecialObj(rs.getInt("specialObj"));
				jobWant.setPositionName(rs.getString("positionName"));
				jobWant.setSalary(rs.getString("salary"));
				jobWant.setWorkCity(rs.getString("workCity"));
				jobWant.setWantMemo(rs.getString("wantMemo"));
				jobWant.setUserObj(rs.getString("userObj"));
				jobWant.setAddTime(rs.getString("addTime"));
				jobWantList.add(jobWant);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return jobWantList;
	}
	/* ������ְ���󣬽�����ְ�����ҵ�� */
	public String AddJobWant(JobWant jobWant) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�������ְ */
			String sqlString = "insert into JobWant(jobTypeObj,specialObj,positionName,salary,workCity,wantMemo,userObj,addTime) values (";
			sqlString += jobWant.getJobTypeObj() + ",";
			sqlString += jobWant.getSpecialObj() + ",";
			sqlString += "'" + jobWant.getPositionName() + "',";
			sqlString += "'" + jobWant.getSalary() + "',";
			sqlString += "'" + jobWant.getWorkCity() + "',";
			sqlString += "'" + jobWant.getWantMemo() + "',";
			sqlString += "'" + jobWant.getUserObj() + "',";
			sqlString += "'" + jobWant.getAddTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "��ְ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��ְ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ����ְ */
	public String DeleteJobWant(int wantId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from JobWant where wantId=" + wantId;
			db.executeUpdate(sqlString);
			result = "��ְɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��ְɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼id��ȡ����ְ */
	public JobWant GetJobWant(int wantId) {
		JobWant jobWant = null;
		DB db = new DB();
		String sql = "select * from JobWant where wantId=" + wantId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				jobWant = new JobWant();
				jobWant.setWantId(rs.getInt("wantId"));
				jobWant.setJobTypeObj(rs.getInt("jobTypeObj"));
				jobWant.setSpecialObj(rs.getInt("specialObj"));
				jobWant.setPositionName(rs.getString("positionName"));
				jobWant.setSalary(rs.getString("salary"));
				jobWant.setWorkCity(rs.getString("workCity"));
				jobWant.setWantMemo(rs.getString("wantMemo"));
				jobWant.setUserObj(rs.getString("userObj"));
				jobWant.setAddTime(rs.getString("addTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return jobWant;
	}
	/* ������ְ */
	public String UpdateJobWant(JobWant jobWant) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update JobWant set ";
			sql += "jobTypeObj=" + jobWant.getJobTypeObj() + ",";
			sql += "specialObj=" + jobWant.getSpecialObj() + ",";
			sql += "positionName='" + jobWant.getPositionName() + "',";
			sql += "salary='" + jobWant.getSalary() + "',";
			sql += "workCity='" + jobWant.getWorkCity() + "',";
			sql += "wantMemo='" + jobWant.getWantMemo() + "',";
			sql += "userObj='" + jobWant.getUserObj() + "',";
			sql += "addTime='" + jobWant.getAddTime() + "'";
			sql += " where wantId=" + jobWant.getWantId();
			db.executeUpdate(sql);
			result = "��ְ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��ְ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
