INSERT INTO `fine-dust`.`warninglevel_entity` (level, type, description) VALUES
                                                                             (1, '초미세먼지 경보', '가장 심각한 상태, 건강에 매우 해로움'),
                                                                             (2, '미세먼지 경보', '건강에 매우 해로울 수 있음'),
                                                                             (3, '초미세먼지 주의보', '건강에 해로울 수 있음'),
                                                                             (4, '미세먼지 주의보', '건강에 약간 해로울 수 있음')
ON DUPLICATE KEY UPDATE type = VALUES(type), description = VALUES(description);