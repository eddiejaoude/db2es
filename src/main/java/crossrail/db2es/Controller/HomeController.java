package crossrail.db2es.Controller;

import crossrail.db2es.Service.DestinationService;
import crossrail.db2es.Service.SourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Autowired
    private SourceService sourceService;

    @Autowired
    private DestinationService destinationService;

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("sourceTotal", this.sourceService.getTotal());
        model.addAttribute("destinationTotal", this.destinationService.getTotal());
        model.addAttribute("progress", (this.destinationService.getTotal() / this.sourceService.getTotal()) * 100);

        return "home";
    }
}
