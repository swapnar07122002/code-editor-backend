package com.swapna.collabeditor.controller;

import com.swapna.collabeditor.dto.FileDto;
import com.swapna.collabeditor.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
@RestController
@RequestMapping("api/files")
public class FileServeController {

    private final FileService fileService;

    @GetMapping("/serve/{projectId}/{fileName:.+}")
    public ResponseEntity<String> serveFile(
            @PathVariable Long projectId,
            @PathVariable String fileName,
            HttpServletRequest request
    ) {
        String decodedFileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);

        FileDto fileDto;
        try {
            fileDto = fileService.getFileByProjectId(projectId, decodedFileName);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
        }

        String content = fileDto.getContent() == null ? "" : fileDto.getContent();

        String ext = "";
        int idx = decodedFileName.lastIndexOf('.');
        if (idx >= 0) ext = decodedFileName.substring(idx + 1).toLowerCase();

        HttpHeaders headers = new HttpHeaders();


        // existing security header
        headers.set("Content-Security-Policy", "frame-ancestors 'self' http://localhost:3000 https://*.vercel.app");

        if ("html".equals(ext) || "htm".equals(ext)) {

            String host = request.getHeader("host");
            String protocol = (host != null && host.contains("localhost")) ? "http" : "https";
            String domain = protocol + "://" + host;

            String prefix = domain + "/api/files/serve/" + projectId + "/";

            String replaced = replaceAliasPaths(content, prefix);

            headers.setContentType(MediaType.TEXT_HTML);
            return new ResponseEntity<>(replaced, headers, HttpStatus.OK);

        } else if ("css".equals(ext)) {

            headers.setContentType(new MediaType("text", "css", StandardCharsets.UTF_8));
            return new ResponseEntity<>(content, headers, HttpStatus.OK);

        } else if ("js".equals(ext) || "mjs".equals(ext)) {

            headers.setContentType(new MediaType("text", "javascript", StandardCharsets.UTF_8));
            return new ResponseEntity<>(content, headers, HttpStatus.OK);

        } else if ("json".equals(ext)) {

            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(content, headers, HttpStatus.OK);

        } else {

            headers.setContentType(MediaType.TEXT_PLAIN);
            return new ResponseEntity<>(content, headers, HttpStatus.OK);
        }
    }

    private static String replaceAliasPaths(String content, String prefix) {
        if (content == null || content.isEmpty()) return content;

        Pattern p = Pattern.compile("(src|href)=(['\"]?)@(.*?)\\2|url\\((['\"]?)@(.*?)\\4\\)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(content);
        StringBuffer sb = new StringBuffer();

        while (m.find()) {
            String replacement;

            if (m.group(1) != null) {
                String attr = m.group(1);
                String quote = m.group(2);
                String rawPath = m.group(3);
                String cleanedPath = rawPath.replaceFirst("^/+","");

                replacement = attr + "=" + quote + prefix + cleanedPath + quote;
            } else {
                String quote = m.group(4);
                String rawPath = m.group(5);
                String cleanedPath = rawPath.replaceFirst("^/+","");

                replacement = "url(" + quote + prefix + cleanedPath + quote + ")";
            }

            replacement = Matcher.quoteReplacement(replacement);
            m.appendReplacement(sb, replacement);
        }

        m.appendTail(sb);
        return sb.toString();
    }
}