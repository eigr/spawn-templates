package io.eigr.spawn.java.service;

import java.util.Map;

public interface PostalCodeService {
    Map<String, String> find(String postalCode);
}
