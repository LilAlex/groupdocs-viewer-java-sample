package com.groupdocs.viewer.sample;

import com.groupdocs.viewer.domain.FileDescription;
import com.groupdocs.viewer.domain.options.FileTreeOptions;
import com.groupdocs.viewer.exception.GroupDocsException;
import com.groupdocs.viewer.handler.input.IInputDataHandler;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageCredentials;
import com.microsoft.azure.storage.StorageCredentialsAccountAndKey;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The type Azure input data handler.
 */
public class AzureInputDataHandler implements IInputDataHandler {
    /// <summary>
    /// The blob delimiter.
    /// </summary>
    private static final String Delimiter = "/";
    /// <summary>
    /// The endpoint template.
    /// </summary>
    private static final String EndpointTemplate = "https://{account-name}.blob.core.windows.net/";
    /// <summary>
    /// The cloud blob container.
    /// </summary>
    private final CloudBlobContainer _container;

    /**
     * Instantiates a new Azure input data handler.
     * @param accountName   the account name
     * @param accountKey    the account key
     * @param containerName the container name
     * @throws URISyntaxException the uri syntax exception
     */
/// <summary>
    /// Initializes a new instance of the <see cref="AzureInputDataHandler"/> class.
    /// </summary>
    /// <param name="accountName"></param>
    /// <param name="accountKey"></param>
    /// <param name="containerName"></param>
    public AzureInputDataHandler(String accountName, String accountKey, String containerName) throws URISyntaxException {
        this(GetEndpoint(accountName), accountName, accountKey, containerName);

    }

    /**
     * Instantiates a new Azure input data handler.
     * @param endpoint      the endpoint
     * @param accountName   the account name
     * @param accountKey    the account key
     * @param containerName the container name
     */
/// <summary>
    /// Initializes a new instance of the <see cref="AzureInputDataHandler"/> class.
    /// </summary>
    /// <param name="endpoint">The endpoint e.g. https://youraccountname.blob.core.windows.net/ </param>
    /// <param name="accountName">The account name.</param>
    /// <param name="accountKey">The account key.</param>
    /// <param name="containerName">The container name.</param>
    public AzureInputDataHandler(URI endpoint, String accountName, String accountKey, String containerName) {
        try {
            StorageCredentials storageCredentials = new StorageCredentialsAccountAndKey(accountName, accountKey);
            CloudStorageAccount cloudStorageAccount = new CloudStorageAccount(storageCredentials, endpoint, null, null, null);
            CloudBlobClient cloudBlobClient = cloudStorageAccount.createCloudBlobClient();
            int serverTimeout = cloudBlobClient.getDefaultRequestOptions().getTimeoutIntervalInMs();
            cloudBlobClient.getDefaultRequestOptions().setTimeoutIntervalInMs(10 * 1000);
            _container = cloudBlobClient.getContainerReference(containerName);
            _container.createIfNotExists();
            cloudBlobClient.getDefaultRequestOptions().setTimeoutIntervalInMs(serverTimeout);
        } catch (StorageException e) {
            throw new GroupDocsException("Unable to recognize that Account Name/Account Key or container name is invalid." + e.getMessage());
        } catch (URISyntaxException e) {
            throw new GroupDocsException("Unable to recognize that Account Name/Account Key." + e.getMessage());
        }
    }

    /**
     * Gets file description.
     * @param guid the guid
     * @return the file description
     */
/// <summary>
    /// Gets the file description.
    /// </summary>
    /// <param name="guid">The unique identifier.</param>
    /// <returns>GroupDocs.Viewer.Domain.FileDescription.</returns>
    @Override
    public FileDescription getFileDescription(String guid) {
        try {
            String blobName = GetNormalizedBlobName(guid);
            CloudBlob blob = _container.getPageBlobReference(blobName);
//            blob.fetchAttributes();
            final FileDescription fileDescription = new FileDescription(blobName, false);
            fileDescription.setLastModificationDate(getDateTimeOrEmptyDate(blob.getProperties().getLastModified()));
            fileDescription.setSize(blob.getProperties().getLength());

            return fileDescription;
        } catch (Exception e) {
            throw new GroupDocsException("Unabled to get file description." + e.getMessage());
        }
    }

    /**
     * Gets file.
     * @param guid the guid
     * @return the file
     */
    @Override
    public InputStream getFile(String guid) {
        try {
            String blobName = GetNormalizedBlobName(guid);
            CloudBlob blob = _container.getPageBlobReference(blobName);
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            blob.download(arrayOutputStream);
            return new ByteArrayInputStream(arrayOutputStream.toByteArray());
        } catch (Exception ex) {
            throw new GroupDocsException("Unabled to get file." + ex.getMessage());
        }
    }

    /**
     * Gets last modification date.
     * @param guid the guid
     * @return the last modification date
     */
/// <summary>
    /// Gets the last modification date.
    /// </summary>
    /// <param name="guid">The unique identifier.</param>
    /// <returns>System.DateTime.</returns>
    @Override
    public Date getLastModificationDate(String guid) {
        FileDescription fileDescription = getFileDescription(guid);
        return fileDescription.getLastModificationDate();
    }

    /**
     * Load file tree list.
     * @param fileTreeOptions the file tree options
     * @return the list
     */
/// <summary>
    /// Loads files/folders structure for specified path
    /// </summary>
    /// <param name="fileTreeOptions">The file tree options.</param>
    /// <returns>System.Collections.Generic.List&lt;GroupDocs.Viewer.Domain.FileDescription&gt;.</returns>
    @Override
    public List<FileDescription> loadFileTree(FileTreeOptions fileTreeOptions) {
        try {
            String path = GetNormalizedBlobName(fileTreeOptions.getPath());
            List<FileDescription> fileTree = GetFileTree(path);
//            switch (fileTreeOptions.getOrderBy())
//            {
//                case Name:
//                    fileTree = fileTreeOptions.getOrderAsc() ? fileTree.OrderBy(_ => _.Name).ToList() : fileTree.OrderByDescending(_ => _.Name).ToList();
//                    break;
//                case LastModificationDate:
//                    fileTree = fileTreeOptions.getOrderAsc() ? fileTree.OrderBy(_ => _.LastModificationDate).ToList() : fileTree.OrderByDescending(_ => _.LastModificationDate).ToList();
//                    break;
//                case Size:
//                    fileTree = fileTreeOptions.getOrderAsc() ? fileTree.OrderBy(_ => _.Size).ToList() : fileTree.OrderByDescending(_ => _.Size).ToList();
//                    break;
//                default:
//                    break;
//            }
            return fileTree;
        } catch (Exception ex) {
            throw new GroupDocsException("Failed to load file tree." + ex.getMessage());
        }
    }

    /// <summary>
    /// Gets the endpoint e.g. https://youraccountname.blob.core.windows.net/
    /// </summary>
    /// <param name="accountName">The account name.</param>
    /// <returns>Endpoint Uri.</returns>
    private static URI GetEndpoint(String accountName) throws URISyntaxException {
        String endpoint = EndpointTemplate.replace("{account-name}", accountName);
        return new URI(endpoint);
    }

    /// <summary>
    /// Gets the file tree.
    /// </summary>
    /// <param name="blobName">The blob name.</param>
    /// <returns>The file tree.</returns>
    private List<FileDescription> GetFileTree(String blobName) throws URISyntaxException, StorageException {
        CloudBlobDirectory directory = _container.getDirectoryReference(blobName);
        List<FileDescription> fileTree = new ArrayList<FileDescription>();
        for (ListBlobItem blob : directory.getContainer().listBlobs()) {
            FileDescription fileDescription;
            CloudBlobDirectory blobDirectory = (CloudBlobDirectory) blob;
            if (blobDirectory != null) {
                fileDescription = new FileDescription(blobDirectory.getPrefix(), true);
            } else {
                CloudBlob blobFile = (CloudBlob) blob;
                fileDescription = new FileDescription(blobFile.getName(), false);
                fileDescription.setSize(blobFile.getProperties().getLength());
                fileDescription.setLastModificationDate(getDateTimeOrEmptyDate(blobFile.getProperties().getLastModified()));
            }
            fileTree.add(fileDescription);
        }
        return fileTree;
    }

    /// <summary>
    /// Gets normalized blob name, updates guid from dir\\file.ext to dir/file.ext
    /// </summary>
    /// <param name="guid">The unique identifier.</param>
    /// <returns>Normalized blob name.</returns>
    private String GetNormalizedBlobName(String guid) {
        return guid.replace("\\+", Delimiter);
    }

    /// <summary>
    /// Gets date time or empty date.
    /// </summary>
    /// <param name="dateTimeOffset">The date time offset.</param>
    /// <returns>Date time or empty date.</returns>
    private Date getDateTimeOrEmptyDate(Date dateTimeOffset) {
        Date emptyDate = new Date(1, 1, 1);
        return dateTimeOffset != null ? dateTimeOffset : emptyDate;
    }
}
