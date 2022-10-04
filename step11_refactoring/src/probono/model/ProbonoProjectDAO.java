package probono.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import probono.model.dto.ProbonoProjectDTO;
import probono.model.entity.Activist;
import probono.model.entity.Probono;
import probono.model.entity.ProbonoProject;
import probono.model.entity.Recipient;
import probono.model.util.PublicCommon;

@Repository
public class ProbonoProjectDAO {

	// 프로보노 프로젝트 저장
	public boolean addProbonoProject(ProbonoProjectDTO probonoProject) throws SQLException {
//		ModelMapper modelMapper = new ModelMapper();
//		ProbonoProject entity = modelMapper.map(probonoProject, ProbonoProject.class);
		
		EntityManager manager = PublicCommon.getEntityManager();
		manager.getTransaction().begin();
		
		boolean result = false;

		try {
			ProbonoProject p = new ProbonoProject();
			p.setProbonoProjectName(probonoProject.getProbonoProjectName());
			p.setProbonoId( Probono.builder().probonoId(probonoProject.getProbonoId()).build() );
			p.setActivistId( Activist.builder().id(probonoProject.getActivistId()).build() );
			p.setReceiveId( Recipient.builder().id(probonoProject.getReceiveId()).build() );
			p.setProjectContent(probonoProject.getProjectContent());
			//manager.createNativeQuery("insert into probono_project (probono_project_name, probono_id, activist_id, receive_id, project_content) values ('value명1','values명2')");
			manager.persist(p);
			manager.getTransaction().commit();

			result = true;

		} catch (Exception e) {
			manager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			manager.close();
		}
		return result;
	}

	// 수정
	// 프로보노 프로젝트 ID로 재능기부자 수정
	public boolean updateProbonoProjectActivist(int projectId, String activistId) throws SQLException {
		EntityManager manager = PublicCommon.getEntityManager();
		manager.getTransaction().begin();
		boolean result = false;

		try {
			manager.find(ProbonoProject.class, projectId).setActivistId( Activist.builder().id( activistId).build() );

			manager.getTransaction().commit();

			result = true;
		} catch (Exception e) {
			manager.getTransaction().rollback();
		} finally {
			manager.close();
		}
		return result;
	}

	// 수정
	// 프로보노 프로젝트 id로 수해자 정보 변경
	public boolean updateProbonoProjectReceive(int probonoProjectId, String receiveId) throws SQLException {
		EntityManager manager = PublicCommon.getEntityManager();
		manager.getTransaction().begin();
		boolean result = false;

		try {
			manager.find(ProbonoProject.class, probonoProjectId).setReceiveId( Recipient.builder().id( receiveId).build() );

			manager.getTransaction().commit();

			result = true;
		} catch (Exception e) {
			manager.getTransaction().rollback();
		} finally {
			manager.close();
		}
		return result;
	}

	// 삭제
	// 프로보노 프로젝트 id로 프로보노 프로젝트 삭제
	public boolean deleteProbonoProject(int probonoProjectId) throws SQLException {
		EntityManager manager = PublicCommon.getEntityManager();
		manager.getTransaction().begin();
		boolean result = false;

		try {
			manager.remove(manager.find(ProbonoProject.class, probonoProjectId));

			manager.getTransaction().commit();

			result = true;
		} catch (Exception e) {
			manager.getTransaction().rollback();
		} finally {
			manager.close();
		}
		return result;
	}

	// 프로보노 프로젝트 id로 해당 프로보노프로젝트 검색
	public ProbonoProjectDTO getProbonoProject(int probonoProjectId) throws SQLException {
		EntityManager manager = PublicCommon.getEntityManager();
		manager.getTransaction().begin();
		ProbonoProjectDTO probonoProject = null;

		try {
			ProbonoProject p = manager.find(ProbonoProject.class, probonoProjectId);
			probonoProject = new ProbonoProjectDTO( p.getProbonoProjectId(), p.getProbonoProjectName(), p.getProbonoId().getProbonoId(), p.getActivistId().getId(), p.getReceiveId().getId(),p.getProjectContent());
		} catch (Exception e) {
			manager.getTransaction().rollback();
		} finally {
			manager.close();
		}
		return probonoProject;
	}

	// 모든 프로보노 프로젝트 검색
	public ArrayList<ProbonoProjectDTO> getAllProbonoProjects() throws SQLException {
		EntityManager manager = PublicCommon.getEntityManager();
		ArrayList<ProbonoProjectDTO> alist = new ArrayList<>();
		List list = null;
		try {
			list = manager.createNativeQuery("SELECT * FROM Probono_Project").getResultList();
			
			Iterator it = list.iterator();
			Object[] obj = null;
			while(it.hasNext()) {
				obj = (Object[]) it.next();
				alist.add(new ProbonoProjectDTO(Integer.parseInt(String.valueOf(obj[0])), String.valueOf(obj[1]), String.valueOf(obj[2]), String.valueOf(obj[3]), String.valueOf(obj[4]), String.valueOf(obj[5])));
			}
			
		} catch (Exception e) {
			manager.getTransaction().rollback();
		} finally {
			manager.close();
		}
		return alist;
	}
}
