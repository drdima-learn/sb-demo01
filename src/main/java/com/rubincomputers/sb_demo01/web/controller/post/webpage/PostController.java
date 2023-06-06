package com.rubincomputers.sb_demo01.web.controller.post.webpage;

import com.rubincomputers.sb_demo01.service.UserService;
import com.rubincomputers.sb_demo01.service.dto.UserDTO;
import com.rubincomputers.sb_demo01.service.dto.UserFormDTO;
import com.rubincomputers.sb_demo01.util.exception.BadSortParameter;
import com.rubincomputers.sb_demo01.web.controller.admin.AbstractAdminController;
import com.rubincomputers.sb_demo01.web.controller.post.AbstractPostController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


@Controller
@Slf4j
@RequestMapping(value = PostController.WEBPAGE_URL)
public class PostController extends AbstractPostController {

    static final String WEBPAGE_URL = "/admin/users";




}
