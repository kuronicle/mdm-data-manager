package net.kuronicle.mdm.web;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.kuronicle.mdm.domain.ColumnDefinition;
import net.kuronicle.mdm.domain.Record;
import net.kuronicle.mdm.service.DataManagerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("data-manager")
public class DataManagerController {

    @Autowired
    DataManagerService dataManagerService;

    @RequestMapping(value = "{orsName}/{baseObjectName}/list")
    String list(Model model, @PathVariable String orsName, @PathVariable String baseObjectName) {

        String baseObjectDispName = dataManagerService.getBaseObjectDisplayName(orsName, baseObjectName);

        Set<ColumnDefinition> columnDefinitions = dataManagerService.getColumnDefinitions(orsName, baseObjectName);
        List<Record> baseObjectRecords = dataManagerService.findAll(orsName, baseObjectName);

        model.addAttribute("orsName", orsName);
        model.addAttribute("baseObjectName", baseObjectName);
        model.addAttribute("baseObjectDispName", baseObjectDispName);
        model.addAttribute("columnDefinitions", columnDefinitions);
        model.addAttribute("baseObjectRecords", baseObjectRecords);

        return "data-manager/list";
    }

    @RequestMapping(value = "{orsName}/{baseObjectName}/create", params = "form", method = RequestMethod.GET)
    String createForm(Model model, @PathVariable String orsName, @PathVariable String baseObjectName) {

        String baseObjectDispName = dataManagerService.getBaseObjectDisplayName(orsName, baseObjectName);

        Set<ColumnDefinition> columnDefinitions = dataManagerService.getColumnDefinitions(orsName, baseObjectName);

        model.addAttribute("orsName", orsName);
        model.addAttribute("baseObjectName", baseObjectName);
        model.addAttribute("baseObjectDispName", baseObjectDispName);
        model.addAttribute("columnDefinitions", columnDefinitions);

        return "data-manager/create";
    }

    @RequestMapping(value = "{orsName}/{baseObjectName}/create", method = RequestMethod.POST)
    String create(@PathVariable String orsName, @PathVariable String baseObjectName, @RequestParam Map<String, String> params) {

        Record baseObjectRecord = new Record();
        for (Entry<String, String> entry : params.entrySet()) {
            baseObjectRecord.addColumn(entry.getKey(), entry.getValue());
        }
        dataManagerService.put(baseObjectRecord);

        return "redirect:/data-manager/" + orsName + "/" + baseObjectName + "/list";
    }

    @RequestMapping(value = "{orsName}/{baseObjectName}/edit", params = "form", method = RequestMethod.GET)
    String editForm(Model model, @PathVariable String orsName, @PathVariable String baseObjectName, @RequestParam String id) {

        String baseObjectDispName = dataManagerService.getBaseObjectDisplayName(orsName, baseObjectName);

        Set<ColumnDefinition> columnDefinitions = dataManagerService.getColumnDefinitions(orsName, baseObjectName);
        Record baseObjectRecord = dataManagerService.findOne(orsName, baseObjectName, id);

        model.addAttribute("orsName", orsName);
        model.addAttribute("baseObjectName", baseObjectName);
        model.addAttribute("baseObjectDispName", baseObjectDispName);
        model.addAttribute("columnDefinitions", columnDefinitions);
        model.addAttribute("baseObjectRecord", baseObjectRecord);

        return "data-manager/edit";
    }

    @RequestMapping(value = "{orsName}/{baseObjectName}/edit", method = RequestMethod.POST)
    String edit(@PathVariable String orsName, @PathVariable String baseObjectName, @RequestParam Map<String, String> params) {

        Record baseObjectRecord = dataManagerService.findOne(orsName, baseObjectName, params.get("id"));
        for (Entry<String, String> entry : params.entrySet()) {
            baseObjectRecord.addColumn(entry.getKey(), entry.getValue());
        }
        dataManagerService.put(baseObjectRecord);

        return "redirect:/data-manager/" + orsName + "/" + baseObjectName + "/list";
    }
    
    @RequestMapping(value = "{orsName}/{baseObjectName}/delete", method = RequestMethod.POST)
    String delete(@PathVariable String orsName, @PathVariable String baseObjectName, @RequestParam String id) {
        dataManagerService.delete(id);
        return "redirect:/data-manager/" + orsName + "/" + baseObjectName + "/list";
    }

    @RequestMapping(value = "{orsName}/{baseObjectName}/edit", params = "goToList")
    String goToList(@PathVariable String orsName, @PathVariable String baseObjectName) {
        return "redirect:/data-manager/" + orsName + "/" + baseObjectName + "/list";
    }
}
