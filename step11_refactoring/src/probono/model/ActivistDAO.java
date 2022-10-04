package probono.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import probono.model.dto.ActivistDTO;
import probono.model.entity.Activist;
import probono.model.util.PublicCommon;

@Repository
/*
 * @Component와 동일
 */
public class ActivistDAO {          
   
	public boolean addActivist(ActivistDTO activist) throws Exception {
		EntityManager em = PublicCommon.getEntityManager();
		em.getTransaction().begin();
		boolean result = false;

		try {
			em.persist(activist.toEntity());
			em.getTransaction().commit();

			result = true;

		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
			em = null;
		}
		return result;
	}

	// 수정
	// 기부자 id로 주요 기부 내용 수정하기
	public boolean updateActivist(String activistId, String major) throws Exception {
		EntityManager em = PublicCommon.getEntityManager();
		em.getTransaction().begin();
		boolean result = false;

		try {
			em.find(Activist.class, activistId).setMajor(major);

			em.getTransaction().commit();

			result = true;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
			em = null;
		}
		return result;
	}

	public boolean deleteActivist(String activistId) throws Exception {
		EntityManager em = PublicCommon.getEntityManager();
		em.getTransaction().begin();
		boolean result = false;

		try {
			em.remove(em.find(Activist.class, activistId));

			em.getTransaction().commit();

			result = true;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
			em = null;
		}
		return result;
	}

	// id로 해당 기부자의 모든 정보 반환
	public ActivistDTO getActivist(String activistId) throws Exception {
		EntityManager em = PublicCommon.getEntityManager();
		em.getTransaction().begin();
		ActivistDTO activist = null;

		try {
			Activist a = em.find(Activist.class, activistId);
			
			
//			activist = new ActivistDTO(a.getId(), a.getName(), a.getPassword(), a.getMajor());
			activist = (new ModelMapper()).map(a, ActivistDTO.class);
			
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
			em = null;
		}
		return activist;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<ActivistDTO> getAllActivists() throws Exception {
		EntityManager em = PublicCommon.getEntityManager();
		List<Activist> list = null;
		ArrayList<ActivistDTO> alist = new ArrayList<>();
		try {
			list = em.createNativeQuery("SELECT * FROM Activist").getResultList();
			
			Iterator it = list.iterator();
			while(it.hasNext()) {
				Object[] obj = (Object[]) it.next();
				alist.add(new ActivistDTO(String.valueOf(obj[0]), String.valueOf(obj[1]), String.valueOf(obj[2]), String.valueOf(obj[3])));
			}
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
			em = null;
		}
		return alist;
	}
}
