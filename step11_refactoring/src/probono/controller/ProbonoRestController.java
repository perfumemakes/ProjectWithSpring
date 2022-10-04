package probono.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import probono.model.ProbonoService;
import probono.model.dto.ActivistDTO;
import probono.model.dto.ProbonoProjectDTO;

@RestController
@RequestMapping("rest")
public class ProbonoRestController {

	@Autowired
	ProbonoService probonoService;

	// 모두 ProbonoProject 검색 메소드
	@GetMapping("/probonoProjectAll")
	public ArrayList<ProbonoProjectDTO> probonoProjectAll() throws Exception {
		
		return probonoService.getAllProbonoProjects();
	}

	@GetMapping("/activistAll")
	public ArrayList<ActivistDTO> activistAll() throws Exception {

		return probonoService.getAllActivists();
	}

	@GetMapping("/activist")
	public ActivistDTO activist(@RequestParam("id") String activistId) throws Exception {
		
		ActivistDTO a = probonoService.getActivist(activistId);
		
		if (a != null) {
			return a;
		} 
		else {
			throw new Exception("존재하지 않는 기부자입니다.");
		}
	}

	@PostMapping("/probonoProjectInsert")
	public boolean probonoProjectInsert(ProbonoProjectDTO probonoProject) throws Exception {

		if (probonoProject.getProbonoProjectName() != null && probonoProject.getProbonoId() != null
				&& probonoProject.getReceiveId() != null && probonoProject.getProjectContent() != null) {
			boolean result = probonoService.addProbonoProject(probonoProject);
			if (result) {
				return true;
			}
		} else {
		}
		return false;
	}

}
