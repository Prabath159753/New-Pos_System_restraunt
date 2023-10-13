package lk.intelleon.bo.custom.impl;

import lk.intelleon.bo.custom.RefBO;
import lk.intelleon.dao.DAOFactory;
import lk.intelleon.dao.custom.RefDAO;
import lk.intelleon.dto.RefDTO;
import lk.intelleon.entity.Ref;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Kavishka Prabath
 * @created : 10/5/2023 - 3:49 PM
 **/

public class RefBOImpl implements RefBO {

    RefDAO refDAO = (RefDAO) DAOFactory.getInstance().getSuperDAO(DAOFactory.DAOType.REF);

    @Override
    public boolean addRef(RefDTO refDTO) throws Exception {
        return refDAO.add(new Ref(refDTO.getRefId(),refDTO.getName(),refDTO.getIdNumber(),refDTO.getTel(),refDTO.getAddress()));
    }

    @Override
    public boolean deleteRef(String id) throws Exception {
        return refDAO.delete(id);
    }

    @Override
    public RefDTO searchRef(String id) throws Exception {
        Ref ref = refDAO.search(id);
        return new RefDTO(ref.getRefId(),ref.getName(),ref.getIdNumber(),ref.getTel(), ref.getAddress());
    }

    @Override
    public boolean updateRef(RefDTO refDTO) throws Exception {
        return refDAO.update(new Ref(refDTO.getRefId(),refDTO.getName(),refDTO.getIdNumber(),refDTO.getTel(),refDTO.getAddress()));
    }

    @Override
    public ArrayList<RefDTO> getAllRefs() throws Exception {
        List<Ref> all = refDAO.getAll();
        ArrayList<RefDTO> refDTOS = new ArrayList<>();

        for (Ref ref : all){
            refDTOS.add(new RefDTO(ref.getRefId(),ref.getName(),ref.getIdNumber(),ref.getTel(),ref.getAddress()));
        }
        return refDTOS;
    }

    @Override
    public String generateRefId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = refDAO.generateRefId();

        if (resultSet.next()) {
            String oldId = resultSet.getString(1);
            String substring = oldId.substring(1, 4);
            int intId = Integer.parseInt(substring);

            intId = intId + 1;
            if (intId < 10) {
                return "R00" + intId;
            } else if (intId < 100) {
                return "R0" + intId;
            } else if (intId < 1000) {
                return "R" + intId;
            }
        }
        return "R001";
    }

}
