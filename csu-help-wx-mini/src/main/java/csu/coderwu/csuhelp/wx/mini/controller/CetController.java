package csu.coderwu.csuhelp.wx.mini.controller;

import csu.coderwu.csuhelp.core.bean.Response;
import csu.coderwu.csuhelp.core.util.ResponseUtil;
import csu.coderwu.tool.cet.api.CetService;
import csu.coderwu.tool.cet.bean.CetScore;
import csu.coderwu.tool.cet.exception.CETException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author : coderWu
 * @date : Created on 9:50 2018/5/30
 */
@RestController
@RequestMapping("/cet")
public class CetController {

    @Autowired
    private CetService cetService;

    @PostMapping("/grades")
    public Response getGrades(@RequestBody Map<String, String> params) {
        String schoolNum = params.get("xh");
        String name = params.get("name");
        if (schoolNum == null || name == null || schoolNum.isEmpty() || name.isEmpty()) {
            return ResponseUtil.badArgument();
        }
        try {
            List<CetScore> cetScoreList = cetService.getHistoryScores(schoolNum, name);
            return ResponseUtil.success(cetScoreList);
        } catch (CETException e) {
            return ResponseUtil.fail(e.getMessage());
        }
    }
}
