function attachImage(fileInput, destinationId) {
    console.log(fileInput.files);
    const fileReader = new FileReader();
    fileReader.onload = () => {
        const dataUrl = fileReader.result;
        const splitter = dataUrl.indexOf(',');
        const encodedImage = dataUrl.slice(splitter + 1);
        const destination = document.getElementById(destinationId);
        destination.value = encodedImage;
    };
    fileReader.readAsDataURL(fileInput.files[0])
}
