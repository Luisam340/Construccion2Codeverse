package app.code.verse.infrastructure.persistence.mapper;

import app.code.verse.domain.model.Policy;
import app.code.verse.infrastructure.persistence.entities.PolicyEntity;

public class PolicyMapper {

    public static PolicyEntity toEntity(Policy policy) {
        if (policy == null) return null;
        PolicyEntity entity = new PolicyEntity();
        entity.setCompanyName(policy.getCompanyName());
        entity.setPolicyNumber(policy.getPolicyNumber());
        entity.setActive(policy.isActive());
        entity.setExpirationDate(policy.getExpirationDate());
        return entity;
    }

    public static Policy toDomain(PolicyEntity entity) {
        if (entity == null) return null;
        Policy policy = new Policy();
        policy.setCompanyName(entity.getCompanyName());
        policy.setPolicyNumber(entity.getPolicyNumber());
        policy.setActive(entity.getActive());
        policy.setExpirationDate(entity.getExpirationDate());
        return policy;
    }

    public static void partialUpdate(Policy source, PolicyEntity target) {
        if (source.getCompanyName() != null) target.setCompanyName(source.getCompanyName());
        if (source.getPolicyNumber() != null) target.setPolicyNumber(source.getPolicyNumber());
        target.setActive(source.isActive());
        if (source.getExpirationDate() != null) target.setExpirationDate(source.getExpirationDate());
    }
}
