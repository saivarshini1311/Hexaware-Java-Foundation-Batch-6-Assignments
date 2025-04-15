package dao;

import entity.Policy;
import exception.PolicyNotFoundException;
import exception.InvalidPolicyException;

import java.util.Collection;

public interface IPolicyService {
    boolean createPolicy(Policy policy) throws InvalidPolicyException;
    
    Policy getPolicy(int policyId) throws PolicyNotFoundException;
    
    Collection<Policy> getAllPolicies();
    
    boolean updatePolicy(Policy policy) throws PolicyNotFoundException, InvalidPolicyException;
    
    boolean deletePolicy(int policyId) throws PolicyNotFoundException;
}
