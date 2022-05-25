package lt.vu.rest;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import lombok.Getter;
import lombok.Setter;
import lt.vu.decorators.IDeleter;
import lt.vu.entities.Customer;
import lt.vu.interceptors.LoggedInvocation;
import lt.vu.persistence.CustomerDAO;
import lt.vu.rest.contracts.CustomerDto;
import lt.vu.services.CustomerService;

@ApplicationScoped
@Path("/customers")
public class CustomerController {

    @Inject
    @Setter
    @Getter
    private CustomerService customerService;

    @Inject
    private EntityManager em;

    @Inject
    private CustomerDAO customerDAO;

    @Inject
    private IDeleter iDeleter;

    private CompletableFuture<String> generateRandomStr = null;

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    @LoggedInvocation
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() {
        List<CustomerDto> customerDtos = new ArrayList<>();
        for(Customer customer : customerService.getAll())
            customerDtos.add(new CustomerDto(customer));
        return Response.ok(customerDtos).build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") final Integer id) {
        return Response.ok(new CustomerDto(customerService.getById(id))).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response create(CustomerDto customerDto) {
        return Response.ok(new CustomerDto(customerService.createCustomer(customerDto))).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(CustomerDto customerDto) {
        return Response.ok(new CustomerDto(customerService.updateCustomer(customerDto))).build();
    }

    @Path("/optimistic")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Response updateOptimistic(CustomerDto customerDto) throws InterruptedException {
        return Response.ok(new CustomerDto(customerService.updateCustomerOptimistic(customerDto))).build();
    }

    @Path("/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response delete(@PathParam("id") final Integer id) {
        return Response.ok(new CustomerDto(iDeleter.deleteById(id))).build();
    }

    @Path("/status")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkStatus() {
        if (generateRandomStr != null && !generateRandomStr.isDone()) {
            return Response.ok("generate CUSTOMER task is in progress...").build();
        }
        return Response.ok("generate CUSTOMER task is in COMPLETED.").build();
    }

    @Path("/create-async")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createAsync(CustomerDto customerDto) throws ExecutionException, InterruptedException {
        Customer customer = new Customer();
        generateRandomStr = CompletableFuture.supplyAsync(this::generateRandomString);
        try {
            String name = generateRandomStr.get();
            customer.setFirstName("FN_" + name);
            customer.setLastName("LN_" + name);
            customerDAO.persist(customer);
        } catch (Exception e) {
            throw e;
        }
        System.out.println("Created customer: FIRST_NAME: " + customer.getFirstName() + ", LAST_NAME: " + customer.getLastName());
        return Response.ok(customer).build();
    }

    public String generateRandomString() {
        StringBuilder sb = new StringBuilder(8);
        for(int i = 0; i < 8; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return sb.toString();
    }


}
