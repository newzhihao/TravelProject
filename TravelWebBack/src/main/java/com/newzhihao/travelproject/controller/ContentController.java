package com.newzhihao.travelproject.controller;

import com.newzhihao.travelproject.pojo.Contents;
import com.newzhihao.travelproject.pojo.Result;
import com.newzhihao.travelproject.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
public class ContentController {
    @Autowired
    private ContentService contentService;

    @GetMapping("/contents")
    public Result selectAll() {
        List<Contents> contents = contentService.selectAll();
        return Result.success(contents);
    }

    @GetMapping("/contents/{contentId}")
    public Result selectById(@PathVariable Integer contentId) {
        Contents content = contentService.selectById(contentId);
        if (content != null) {
            return Result.success(content);
        } else {
            return Result.error("Content not found");
        }
    }

    @GetMapping("/contents/personal/{authorAccount}")
    public Result getPersonalContents(@PathVariable String authorAccount) {
        List<Contents> contents = contentService.getPersonalContents(authorAccount);
        if (!contents.isEmpty()) {
            return Result.success(contents);
        } else {
            return Result.error("No contents found for this author");
        }
    }

    @GetMapping("/getCardListContents")
    public Result getCardListContents() {
        return Result.success(contentService.getCardListContents());
    }

    @DeleteMapping("/deleteContent/{contentId}")
    public Result deleteContent(@PathVariable Integer contentId) {
        contentService.deleteContent(contentId);
        return Result.success();
    }

    @GetMapping("/contents/search/{words}")
    public Result getContentsBySearch(@PathVariable String words) {
        return Result.success(contentService.getContentsBySearch(words));
    }

    @GetMapping("/contents/category/{category}")
    public Result getContentsByCategory(@PathVariable String category) {
        return Result.success(contentService.getContentsByCategory(category));
    }

    /*
     *                       _oo0oo_
     *                      o8888888o
     *                      88" . "88
     *                      (| -_- |)
     *                      0\  =  /0
     *                    ___/`---'\___
     *                  .' \\|     |// '.
     *                 / \\|||  :  |||// \
     *                / _||||| -:- |||||- \
     *               |   | \\\  -  /// |   |
     *               | \_|  ''\---/''  |_/ |
     *               \  .-\__  '-'  ___/-. /
     *             ___'. .'  /--.--\  `. .'___
     *          ."" '<  `.___\_<|>_/___.' >' "".
     *         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     *         \  \ `_.   \_ __\ /__ _/   .-` /  /
     *     =====`-.____`.___ \_____/___.-`___.-'=====
     *                       `=---='
     *     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *               佛祖保佑         永无BUG
     *     电子佛祖已加载，代码运行顺顺当当，bug退散！
     */
}