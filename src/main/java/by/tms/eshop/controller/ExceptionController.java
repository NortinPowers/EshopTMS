package by.tms.eshop.controller;

import static by.tms.eshop.utils.Constants.MappingPath.ERROR_403;
import static by.tms.eshop.utils.Constants.MappingPath.ERROR_500;
import static by.tms.eshop.utils.Constants.MappingPath.SOME_ERROR;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ExceptionController {

    @GetMapping("/error-500")
    public ModelAndView showError500Page() {
        return new ModelAndView(ERROR_500);
    }

    @GetMapping("/some-error")
    public ModelAndView showSomeErrorPage() {
        return new ModelAndView(SOME_ERROR);
    }

    @GetMapping("/error-403")
    public ModelAndView showError403Page() {
        return new ModelAndView(ERROR_403);
    }
}
